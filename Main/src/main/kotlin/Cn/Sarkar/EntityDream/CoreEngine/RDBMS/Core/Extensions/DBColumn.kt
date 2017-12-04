/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.TinyInt
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.VarChar
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import kotlin.reflect.KProperty

typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int

 abstract class AbstractDBColumn<KOTLINDATATYPE> : IDBColumn<KOTLINDATATYPE> {
     override fun getValue(thisRef: IDBEntity, property: KProperty<*>): KOTLINDATATYPE {
         return this.getDefaultValue()
     }

     override fun setValue(thisRef: IDBEntity, property: KProperty<*>, value: KOTLINDATATYPE) {

     }

     protected fun thisAddToTable(){
        this.Table.Columns = arrayOf(this, *Table.Columns)
    }
}

/*Begin Extension Infix Functions*/
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.notNull(notNull: Boolean) = this.apply { this.NotNull = notNull }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.autoInc(autoIncrement: Boolean) = this.apply { this.AutoIncrement.autoIncrement = autoIncrement }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.unique(unique: Boolean) = this.apply { this.Unique = unique}
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.index(index: Boolean) = this.apply { this.Index = index}
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.default(default: KOTLINDATATYPE) = this.apply { this.DataType.DefaultValue = default }
/*End Extension Infix Functions*/

class DBIdColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(Unsigned = true),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(true),
        override var Unique: Boolean = true,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = true
) : AbstractDBColumn<Int>(){
    init {
        thisAddToTable()
    }
}
fun IDBTable.idColumn(ColumnName: String) : DBIdColumn = DBIdColumn(this,  ColumnName)

class DBStringColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<String> = VarChar(100),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = false
) : AbstractDBColumn<String>(){
    init {
        thisAddToTable()
    }
}
fun IDBTable.stringColumn(ColumnName: String, length: Int = 100) : DBStringColumn = DBStringColumn(this,  ColumnName, DataType = VarChar(length))

class DBIntColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = false
) : AbstractDBColumn<Int>(){
    init {
        thisAddToTable()
    }
}
fun IDBTable.intColumn(ColumnName: String) : DBIntColumn = DBIntColumn(this,  ColumnName)

class DBTinyIntColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Byte> = TinyInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = false
) : AbstractDBColumn<Byte>() {
    init {
        thisAddToTable()
    }
}
fun IDBTable.byteColumn(ColumnName: String) : DBTinyIntColumn = DBTinyIntColumn(this,  ColumnName)