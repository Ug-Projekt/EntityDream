package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.MySqlQueryTranslatorTest
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.MySqlDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import Cn.Sarkar.EntityDream.Pipeline.Extension.installFeature
import org.junit.Assert
import org.junit.Test
import java.sql.DriverManager
import javax.jws.soap.SOAPBinding

object Users : IDBTable {
    val ID = DBIdColumn(this, "ID")
    val Name = DBStringColumn(this, "Name")
    val Pwd = DBStringColumn(this, "Pwd")
    val EMail = DBStringColumn(this, "EMail")
    val Age = DBIntColumn(this, "Age")

    override var TableName: String = "User"
    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(ID)
    override var Columns: Array<IDBColumn<*>> = arrayOf(ID, Name, Pwd, EMail, Age)
}

internal class DataContextKtTest {

    object db : MySqlDataContext(DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/Hello", "yeganaaa", "Developer653125")) {


        val logger = object : PipeLineFeature<IPipeLineSubject, IDataContext>() {
            override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.after, "Logger") }
            override val info: FeatureInfo by lazy { FeatureInfo("Logger", "Demo", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

            override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
                if (subject is TranslationSubject) {
                    println(subject.translationResult!!.fullSqlQuery)
                }


            }
        }

        init {
            pipeLine.installFeature(logger)
        }
    }


    @Test
    fun executeSelectQuery() {

        var expr = (Users.SelectQuery where { Users.Age greater 18 }) uCase Users.EMail orderBy Users.Age


//        var expr = Users.SelectQuery.uCase(Users.Pwd).orderByDesc(Users.Age).take(100)

        val result = db.executeSelectQuery(expr)

        result.forEach {
            println("Age: ${it[Users.Age]}, EMail: ${it[Users.EMail]}")
        }
    }

    @Test
    fun executeUpdateQuery() {

    }

}