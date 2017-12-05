package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression
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

    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(ID)
}

class User(DataContext: IDataContext) : DBEntity(DataContext, Users)
{
    var ID by Users.ID
    var Name by Users.Name
    var Pwd by Users.Pwd
    var Age by Users.Age
    var EMail by Users.EMail
}

internal class DataContextKtTest {
    object db : MySqlDataContext(DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/Hello", "yeganaaa", "Developer653125")) {

        val Users = dbCollection(Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.Users) { User(it) }
    }

    val logger = object : PipeLineFeature<IPipeLineSubject, IDataContext>() {
        override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.after, "Logger") }
        override val info: FeatureInfo by lazy { FeatureInfo("Logger", "Demo", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

        override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
//                println(subject.operationName + "***********" + subject::class.java.simpleName)

            if (subject is TranslationSubject) {

                println(">>>" + subject.translationResult!!.fullSqlQuery)
            }
        }
    }

    @Test
    fun executeSelectQuery() {
        db.pipeLine.installFeature(logger)

        var usrs = db.Users where {Users.Age greater 17}

        usrs.forEach {
            println("ID: ${it.ID} Name: ${it.Name} Age: ${it.Age} Pwd: ${it.Pwd} EMail: ${it.EMail}")
        }

        usrs orderByDesc Users.Age

        usrs.forEach {

            it.Name = "Okay1"

            println("ID: ${it.ID} Name: ${it.Name} Age: ${it.Age} Pwd: ${it.Pwd} EMail: ${it.EMail}")
        }


        db.saveChanges()
    }
}