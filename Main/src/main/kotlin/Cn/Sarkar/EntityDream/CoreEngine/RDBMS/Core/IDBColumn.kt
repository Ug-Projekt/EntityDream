package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import java.io.Serializable
import kotlin.properties.ReadWriteProperty

class AutoIncrementProperty(var autoIncrement: Boolean, var start: Int = 1, var step: Int = 1)
{
    companion object {
        val default = AutoIncrementProperty(true, 0, 1)
        val none = AutoIncrementProperty(false, 0, 1)
    }
}

interface IDBColumn<KOTLINDATATYPE> : ReadWriteProperty<IDBEntity, KOTLINDATATYPE> {
    var Table: IDBTable
    var ColumnName: String
    var NotNull: Boolean
    var DataType: IDBDataType<KOTLINDATATYPE>
    var AutoIncrement: AutoIncrementProperty
    var ForeignKey: IDBColumn<*>?
    var Comment: String
    var IndexLength: Int
}

var IDBColumn<*>.IsPrimaryKey: Boolean
get() = Table.PrimaryKey!!.columns.contains(this)
set(value) {
    if (value) {
        if (!Table.PrimaryKey.columns.contains(this)) Table.PrimaryKey = PrimaryKey("PrimaryKey", *Table.PrimaryKey.columns, this)
    }
    else
        if (Table.PrimaryKey.columns.contains(this)) Table.PrimaryKey = PrimaryKey("PrimaryKey",*Table.PrimaryKey.columns.filter { it != this }.toTypedArray())
}

fun <T> IDBColumn<T>.getDefaultValue() : T = DataType.DefaultValue
fun <T> IDBColumn<T>.setDefaultValue(value: T){
    DataType.DefaultValue = value
}
fun <T> IDBColumn<T>.isUnsigned() = if (DataType is IDBNumberType) (DataType as IDBNumberType).Unsigned else false