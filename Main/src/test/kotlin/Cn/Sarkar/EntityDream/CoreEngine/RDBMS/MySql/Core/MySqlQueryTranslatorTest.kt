package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.VarChar
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ValuesCache
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.*
import org.junit.Test

typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int

internal class MySqlQueryTranslatorTest {
    class DBIdColumn(override var Table: IDBTable, override var ColumnName: String, override var NotNull: Boolean = true, override var DataType: IDataType<Int> = DBInt(Unsigned = true), override var AutoIncrement: Boolean = true, override var Unique: Boolean = true, override var ForeignKey: IDBColumn<*>? = null, override var Index: Boolean = true) : IDBColumn<Int>
    class DBStringColumn(override var Table: IDBTable, override var ColumnName: String, override var NotNull: Boolean = true, override var DataType: IDataType<String> = VarChar(100), override var AutoIncrement: Boolean = false, override var Unique: Boolean = false, override var ForeignKey: IDBColumn<*>? = null, override var Index: Boolean = false) : IDBColumn<String>
    class DBIntColumn(override var Table: IDBTable, override var ColumnName: String, override var NotNull: Boolean = true, override var DataType: IDataType<Int> = DBInt(), override var AutoIncrement: Boolean = false, override var Unique: Boolean = false, override var ForeignKey: IDBColumn<*>? = null, override var Index: Boolean = false) : IDBColumn<Int>

    object Users : IDBTable {
        val ID = DBIdColumn(this, "ID")
        val Name = DBStringColumn(this, "Name")
        val EMail = DBStringColumn(this, "EMail")
        val Age = DBIntColumn(this, "Age")

        override var TableName: String = "User"
        override var PrimaryKey: IDBColumn<*> = ID
        override var Columns: Array<IDBColumn<*>> = arrayOf(ID, Name, EMail, Age)
    }

    class UserEntity(override var DataContext: IDataContext) : IDBEntity {
        override var DBTable: IDBTable = Users
        override var fieldValues: ValuesCache = ValuesCache()
    }

    val translator: IQueryTranslator
        get() = MySqlQueryTranslator()

    @Test
    fun translate() {
        var expr = Users.SelectQuery.where { Users.Age greater 18 and (Users.Age less 30) }.slice() avg Users.Age count Users.ID

        val result = translator.Translate(expr)
        println(result.fullSqlQuery)
    }
}