package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import kotlin.properties.ReadWriteProperty

class AutoIncrementProperty(var autoIncrement: Boolean, var start: Int = 1, var step: Int = 1)
{
    companion object {
        val default = AutoIncrementProperty(true, 1, 1)
        val none = AutoIncrementProperty(false, 1, 1)
    }
}

data class ForeignKey(var column: IDBColumn<*>, var referenced: IDBColumn<*>)

interface IDBColumn<KOTLINDATATYPE> : ReadWriteProperty<IDBEntity, KOTLINDATATYPE> {
    var Table: IDBTable

    var ColumnName: String
    var NotNull: Boolean
    var DataType: IDataType<KOTLINDATATYPE>
    var AutoIncrement: AutoIncrementProperty
    var Unique: Boolean
    var ForeignKey: ForeignKey?
    var Index: Boolean
}

fun <T> IDBColumn<T>.getDefaultValue() : T = DataType.DefaultValue
fun <T> IDBColumn<T>.setDefaultValue(value: T){
    DataType.DefaultValue = value
}
fun <T> IDBColumn<T>.isUnsigned() = if (DataType is IDBNumberType) (DataType as IDBNumberType).Unsigned else false