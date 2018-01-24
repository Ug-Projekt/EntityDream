package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.CreateTableSubjet
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.CreateTable.CreateTableExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.MySqlQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.MySqlDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
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
        val Money = doubleColumn("Money") notNull false comment "پۇلى"
        val BirthDay = dateTimeColumn("BirthDay") notNull true comment "تۇغۇلغان ۋاقتى"
        val CompanyID = intColumn("CompanyID") notNull true reference Company.ID unsigned true comment "CompanyID"

        init {
//            unique(Name, Pwd)
            index(Name, Pwd) unique true
        }
//        val Avatar = byteArrayColumn("Avatar") notNull false comment "باش سۈرەت"

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

        val ID = idColumn("ID") comment "ID"
        val Name = stringColumn("Name") comment "ئىسمى"
        var WebSite = stringColumn("WebSite") comment "تور ئادېرسى"
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

        override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.after, "Logger") }
        override val info: FeatureInfo by lazy { FeatureInfo("Logger", "Demo", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

        override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
//                println((++index).toString() + "---" + subject.Name + "***********" + subject::class.java.simpleName)

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

        val company = db.Companies.first { Company.WebSite equals "kkkkk" }

        val usr = User(db).apply {
            Name = "يىڭى ئەزا"
            this.Age = 20
            this.EMail = "yeganaaa@hotmail.com"
            this.Pwd = "Developer653125"
            this.Money = 22.45
            this.Company = company
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
        db.Companies.where { Company.WebSite equals "http://www.sarkar.cn" }.mid(Company.WebSite, 2, 4) .first().Users.mid(User.Name, 2, 3).forEach {
            println(it.Name)
//            it.Name = "ئىسمى"
        }

        db.saveChanges()
    }

    @Test
    fun printColumnDml(){
        println(db.createNewTables(User, Company))
    }



}











































