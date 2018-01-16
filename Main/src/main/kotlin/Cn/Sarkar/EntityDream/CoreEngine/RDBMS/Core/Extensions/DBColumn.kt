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
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IsPrimaryKey
import org.joda.time.DateTime
import java.io.InputStream
import java.sql.Date
import java.sql.Time

/*Begin Extension Infix Functions*/
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.notNull(notNull: Boolean) = this.apply { this.NotNull = notNull }
infix fun <KOTLINDATATYPE : Number> IDBColumn<KOTLINDATATYPE>.unsigned(unsigned: Boolean) = this.apply { (DataType as IDBNumberType).Unsigned = unsigned }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.autoInc(autoIncrement: Boolean) = this.apply { this.AutoIncrement.autoIncrement = autoIncrement }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.unique(unique: Boolean) = this.apply { this.Unique.isUnique = unique }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.uniqueGroupIndex(groupIndex: Int) = this.apply { this.Unique.uniqueGroupIndex = groupIndex }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.indexGroupIndex(groupIndex: Int) = this.apply { this.Index.groupIndex = groupIndex }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.index(index: Boolean) = this.apply { this.Index.isIndex = index }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.uniqueIndex(unique: Boolean) = this.apply { this.Index.isUnique = unique; this.Index.isIndex = true }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.default(default: KOTLINDATATYPE) = this.apply { this.DataType.DefaultValue = default }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.reference(column: IDBColumn<KOTLINDATATYPE>) = this.apply { ForeignKey = column }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.size(size: Int) = this.apply { if (DataType is IDBPlainScaledDataType<*>) (DataType as IDBPlainScaledDataType<*>).ScaleValue = size }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.precision(precisionValue: Int) = this.apply { if (DataType is IDBScaledType<*>) (DataType as IDBScaledType<*>).PrecisionValue = precisionValue }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.comment(comment: String) = this.apply { this.Comment = comment }
infix fun <KOTLINDATATYPE> IDBColumn<KOTLINDATATYPE>.primaryKey(isPrimaryKey: Boolean) = this.apply { this.IsPrimaryKey = isPrimaryKey }




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
            is NChar -> {
                val dt = DataType as NChar
                DataType = Char(dt.ScaleValue, dt.DefaultValue)
            }
            is NVarChar -> {
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
        override var DataType: IDBDataType<Int> = DBInt(Unsigned = true),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(true),
        override var ForeignKey: IDBColumn<*>? = null
) : AbstractDBColumnConnector<Int>() {
    init {
        setup()
        IsPrimaryKey = true
    }
}

fun IDBTable.idColumn(ColumnName: String) = DBIdColumn(this, ColumnName)


class DBStringColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDBDataType<String> = NVarChar(100),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Int> = DBInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Byte> = TinyInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Short> = SmallInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Float> = DBFloat(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Double> = DBDouble(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<ByteArray> = Binary(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Date> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Date(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<Time> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Time(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
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
        override var DataType: IDBDataType<DateTime> = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.DateTime(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var ForeignKey: IDBColumn<*>? = null
) : AbstractDBColumnConnector<DateTime>() {
    init {
        setup()
    }
}

fun IDBTable.dateTimeColumn(ColumnName: String) = DBDateTimeColumn(this, ColumnName)

