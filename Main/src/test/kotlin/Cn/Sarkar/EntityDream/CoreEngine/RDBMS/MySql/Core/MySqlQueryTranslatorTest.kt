package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.DBIdColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.DBIntColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.DBStringColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.Users
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import org.junit.Test


internal class MySqlQueryTranslatorTest {

    val translator: IQueryTranslator
        get() = MySqlQueryTranslator()

    @Test
    fun translate() {
        var expr = Users.SelectQuery

        val result = translator.Translate(expr)
        println(result.fullSqlQuery)
    }


    @Test
    fun ULanguage(){

    }
}