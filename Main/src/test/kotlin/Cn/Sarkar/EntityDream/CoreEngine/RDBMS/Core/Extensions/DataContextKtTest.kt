package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.MySqlQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.MySqlDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import Cn.Sarkar.EntityDream.Pipeline.Extension.installFeature
import org.joda.time.DateTime
import org.junit.Test
import java.sql.DriverManager


class User(DataContext: IDataContext) : DBEntity(DataContext, User) {
    companion object : DBTable("User") {
        override var Comment: String = "ئەزا"
        val ID = idColumn("ID") comment "ID" primaryKey true
        val Name = stringColumn("Name") isN true default "Name@Empty" size 100 comment "ئىسمى"
        val Pwd = stringColumn("Pwd") isN false size 50 comment "پارولى" default "Password@Empty"
        val Age = byteColumn("Age") unsigned true notNull true default 0 comment "يىشى"
        val EMail = stringColumn("EMail") size 200 comment "ئىلخەت"
        val Money = doubleColumn("Money") notNull false precision 4 comment "پۇلى"
        val BirthDay = dateTimeColumn("BirthDay") notNull true comment "تۇغۇلغان ۋاقتى"
        val CompanyID = intColumn("CompanyID") notNull true reference Company.ID unsigned true comment "CompanyID"
    }

    var ID by User.ID
    var Name by User.Name
    var Pwd by User.Pwd
    var Age by User.Age
    var EMail by User.EMail
    var Money by User.Money
    var BirthDay by User.BirthDay
    var CompanyID by User.CompanyID
    var Company by hasOne(User.CompanyID){ Company(it) }
}

class Company(DataContext: IDataContext) : DBEntity(DataContext, Company) {
    companion object : DBTable("Company") {

        val ID = idColumn("ID")
        val Name = stringColumn("Name")
        var WebSite = stringColumn("WebSite")
    }

    var ID by Company.ID
    var Name by Company.Name
    var WebSite by Company.WebSite
    val Users: QueriableCollection<User> by hasMany(User.CompanyID) { User(it) }
}

internal class DataContextKtTest {

    object db : MySqlDataContext(DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/Hello", "yeganaaa", "Developer653125"))
    {
        val Users = dbCollection(User) { User(it) }
        val Companies = dbCollection(Company) { Company(it) }
    }

    val logger = object : PipeLineFeature<IPipeLineSubject, IDataContext>() {

        var index = 0

        override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.after, "Logger") }
        override val info: FeatureInfo by lazy { FeatureInfo("Logger", "Demo", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

        override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
//                println((++index).toString() + "---" + subject.operationName + "***********" + subject::class.java.simpleName)

            if (subject is TranslationSubject) {

                println("Generated SQL ->> " + subject.translationResult!!.fullSqlQuery)
            }
        }
    }

    init {
        db.pipeLine.installFeature(logger)
    }

    @Test
    fun executeSelectQuery() {

        val result = db.Users
        result.forEach {
//            println("Age: ${it.Age}, Name: ${it.Name}, Money: ${it.Money} ID: ${it.ID}")
            println("${it.Name}, ${it.BirthDay.toString("yyyy/MM/dd HH:mm:ss")}")
        }
    }

    @Test
    fun delete() {
        val all = ArrayList<User>()

        db.Users.forEach {
            all.add(it)
        }

        all.forEach {
            db.Users.remove(it)
        }

        db.saveChanges()
    }

    @Test
    fun selectCompanies() {
        db.Companies.forEach {
            println(it.Name)
        }
        println(db.saveChanges())
    }

    @Test
    fun insertCompany(){

        val company = db.Companies.first { Company.WebSite equals "http://www.sarkar.cn" }

        val usr = User(db).apply {
            Name = "北京-白小飞"
            this.Age = 20
            this.EMail = "北京-白小飞@163.com"
            this.Pwd = "北京-白小飞"
            this.Money = 664.414
            this.CompanyID = company.ID
            this.BirthDay = DateTime(1994, 3, 1, 8, 9, 10)

        }

        db.Users.add(usr)

        println(usr.ID)
        db.saveChanges()
        println(usr.ID)

    }

    @Test
    fun selectCompany() {

//        val newCompany = db.Companies.single { Companys.Name equals "NewCopany" }
        val avgMoney = db.Users.where { User.Name startsWith "Hell" and (User.Age greater 18) } skip 0 take 10 avg User.Money

        println(avgMoney)

//        db.saveChanges()
    }

    @Test
    fun selectCompanyUser()
    {
        db.Users.where { User.BirthDay less DateTime(2000, 1, 1, 1, 2, 3) }.forEach {
            println(it.BirthDay.toString("yyyy/MM/dd HH:mm:ss"))
        }
    }

    @Test
    fun printColumnDml(){
        val tr = MySqlQueryTranslator(db)
        tr.apply {
            println(User.renderToCreateTableSqlString())
        }
    }
}

































