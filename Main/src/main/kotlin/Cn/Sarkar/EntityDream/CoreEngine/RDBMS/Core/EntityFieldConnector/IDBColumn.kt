package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

interface IDBColumn<KOTLINDATATYPE> {
    var Table: IDBTable

    var ColumnName: String
    var NotNull: Boolean
    var DataType: IDataType<KOTLINDATATYPE>
    var AutoIncrement: Boolean
    var Unique: Boolean
    var ForeignKey: IDBColumn<*>?
    var Index: Boolean
}

fun <T> IDBColumn<T>.getDefaultValue() : T = DataType.DefaultValue
fun <T> IDBColumn<T>.setDefaultValue(value: T){
    DataType.DefaultValue = value
}
fun <T> IDBColumn<T>.isUnsigned() = if (DataType is IDBNumberType) (DataType as IDBNumberType).Unsigned else false