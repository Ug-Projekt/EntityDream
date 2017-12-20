package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.MySqlDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import Cn.Sarkar.EntityDream.Pipeline.Extension.installFeature
import org.junit.Test
import java.sql.DriverManager


object Users : DBTable() {

    val ID = idColumn("ID")
    val Name = stringColumn("Name", 200)
    val Pwd = stringColumn("Pwd")
    val Age = byteColumn("Age")
    val EMail = stringColumn("EMail")
    val Money = doubleColumn("Money") notNull false
    val CompanyID = intColumn("CompanyID") notNull true

    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(ID)
}

typealias users = Users

object Companys : DBTable("Company") {

    val ID = idColumn("ID")
    val Name = stringColumn("Name", 200)
    var WebSite = stringColumn("WebSite", 200)

    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(ID)
}

class User(DataContext: IDataContext) : DBEntity(DataContext, Users) {
    var ID by Users.ID
    var Name by Users.Name
    var Pwd by Users.Pwd
    var Age by Users.Age
    var EMail by Users.EMail
    var Money by Users.Money
    var CompanyID by users.CompanyID
}

class Company(DataContext: IDataContext) : DBEntity(DataContext, Companys) {
    var ID by Companys.ID
    var Name by Companys.Name
    var WebSite by Companys.WebSite
}

internal class DataContextKtTest {
    object db : MySqlDataContext(DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/Hello", "yeganaaa", "Developer653125")) {

        val Users = dbCollection(users) { User(it) }
        val Companies = dbCollection(Companys) { Company(it) }
    }

    val logger = object : PipeLineFeature<IPipeLineSubject, IDataContext>() {
        override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.after, "Logger") }
        override val info: FeatureInfo by lazy { FeatureInfo("Logger", "Demo", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

        override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
//                println(subject.operationName + "***********" + subject::class.java.simpleName)

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

        val result = db.Users.where { Users.Name notEquals Users.EMail } uCase Users.Name skip 3 take 4

        result.forEach {
            println("Age: ${it.Age}, Name: ${it.Name}, Money: ${it.Money} ID: ${it.ID}")
        }

    }

    @Test
    fun delete() {
        val entitys = db.Users.where { Users.Name equals "aaa" or (Users.Name equals "bbb") or (Users.Name equals "ccc") }

        entitys.forEach {
            db.Users.remove(it)
        }

        println(db.saveChanges())
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

        val company = db.Companies.first { Companys.WebSite equals "http://www.sarkar.cn" }

        val usr = User(db).apply {
            Name = "ئابدۇلئىزىز"
            this.Age = 20
            this.EMail = "abduleziz@163.com"
            this.Pwd = "abduleziz008"
            this.Money = 664.414
            this.CompanyID = company.ID
        }

        db.Users.add(usr)

        println(usr.ID)
        db.saveChanges()
        println(usr.ID)
    }
}


