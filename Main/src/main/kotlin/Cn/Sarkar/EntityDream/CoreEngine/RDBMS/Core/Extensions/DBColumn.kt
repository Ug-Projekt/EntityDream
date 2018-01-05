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
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Char
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import org.joda.time.DateTime
import java.sql.Date
import java.sql.Time

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
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.size(size: Int) = this.apply { if (DataType is IDBPlainScaledDataType<*>) (DataType as IDBPlainScaledDataType<*>).ScaleValue = size }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.precision(precisionValue: Int) = this.apply { if (DataType is IDBScaledType<*>) (DataType as IDBScaledType<*>).PrecisionValue = precisionValue }

/**
 * Is NChar Or NVarChar
 */
infix fun IDBColumn<String>.isN(isNcharorNVarchar: Boolean) = this.apply {
    if (isNcharorNVarchar) {
        when (DataType) {
            is Char -> {
                val dt = DataType as Char
                DataType = NChar(dt.ScaleValue, dt.DefaultValue)
            }
            is VarChar -> {
                val dt = DataType as VarChar
                DataType = NVarChar(dt.ScaleValue, dt.DefaultValue)
            }
        }
    }
    else
    {
        when (DataType) {
            is Char -> {
                val dt = DataType as NChar
                DataType = Char(dt.ScaleValue, dt.DefaultValue)
            }
            is VarChar -> {
                val dt = DataType as NVarChar
                DataType = VarChar(dt.ScaleValue, dt.DefaultValue)
            }
        }
    }
}
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

fun IDBTable.stringColumn(ColumnName: String) = DBStringColumn(this, ColumnName)

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

class DBShortColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Short> = SmallInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Short>() {
    init {
        setup()
    }
}

fun IDBTable.shortColumn(ColumnName: String) = DBTinyIntColumn(this, ColumnName)

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

class DBByteArrayColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<ByteArray> = Binary(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<ByteArray>() {
    init {
        setup()
    }
}

fun IDBTable.byteArrayColumn(ColumnName: String) = DBByteArrayColumn(this, ColumnName)

class DBDateColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Date> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Date(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Date>() {
    init {
        setup()
    }
}

fun IDBTable.dateColumn(ColumnName: String) = DBDateColumn(this, ColumnName)

class DBTimeColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Time> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Time(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<Time>() {
    init {
        setup()
    }
}

fun IDBTable.timeColumn(ColumnName: String) = DBTimeColumn(this, ColumnName)

class DBDateTimeColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<DateTime> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.DateTime(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: IDBColumn<*>? = null,
        override var Index: Boolean = false
) : AbstractDBColumnConnector<DateTime>() {
    init {
        setup()
    }
}

fun IDBTable.dateTimeColumn(ColumnName: String) = DBDateTimeColumn(this, ColumnName)

