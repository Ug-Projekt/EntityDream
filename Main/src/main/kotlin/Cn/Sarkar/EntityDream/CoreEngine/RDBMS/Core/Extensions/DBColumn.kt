/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.AutoIncrementProperty
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.AbstractDBColumnConnector
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.NVarChar
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.TinyInt
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int
typealias DBFloat = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Float
typealias DBDouble = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Double

/*Begin Extension Infix Functions*/
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.notNull(notNull: Boolean) = this.apply { this.NotNull = notNull }
infix fun <KOTLINDATATYPE : Number> IDBColumn<KOTLINDATATYPE>.unsigned(unsigned: Boolean) = this.apply { (DataType as IDBNumberType).Unsigned = unsigned }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.autoInc(autoIncrement: Boolean) = this.apply { this.AutoIncrement.autoIncrement = autoIncrement }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.unique(unique: Boolean) = this.apply { this.Unique = unique }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.index(index: Boolean) = this.apply { this.Index = index }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.default(default: KOTLINDATATYPE) = this.apply { this.DataType.DefaultValue = default }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.reference(column: IDBColumn<KOTLINDATATYPE>) = this.apply { ForeignKey = column }
/*End Extension Infix Functions*/


class DBIdColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(Unsigned = true),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(true),
        override var Unique: Boolean = true,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = true
) : AbstractDBColumnConnector<Int>() {
    init {
        setup()
    }
}

fun IDBTable.idColumn(ColumnName: String) = DBIdColumn(this, ColumnName)


class DBStringColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<String> = NVarChar(100),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<String>() {
    init {
        setup()
    }
}

fun IDBTable.stringColumn(ColumnName: String, length: Int = 100) = DBStringColumn(this, ColumnName, DataType = NVarChar(length))

class DBIntColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Int>() {
    init {
        setup()
    }
}

fun IDBTable.intColumn(ColumnName: String) = DBIntColumn(this, ColumnName)

class DBTinyIntColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Byte> = TinyInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Byte>() {
    init {
        setup()
    }
}

fun IDBTable.byteColumn(ColumnName: String) = DBTinyIntColumn(this, ColumnName)

class DBFloatColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Float> = DBFloat(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Float>() {
    init {
        setup()
    }
}

fun IDBTable.floatColumn(ColumnName: String) = DBFloatColumn(this, ColumnName)

class DBDoubleColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Double> = DBDouble(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Double>() {
    init {
        setup()
    }
}

fun IDBTable.doubleColumn(ColumnName: String) = DBDoubleColumn(this, ColumnName)