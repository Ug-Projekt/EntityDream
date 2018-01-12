package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import kotlin.properties.ReadWriteProperty

class AutoIncrementProperty(var autoIncrement: Boolean, var start: Int = 1, var step: Int = 1)
{
    companion object {
        val default = AutoIncrementProperty(true, 1, 1)
        val none = AutoIncrementProperty(false, 1, 1)
    }
}

data class UniqueProperty(var isUnique: Boolean = false, var uniqueGroupIndex: Int = 0)
data class IndexProperty(var isIndex: Boolean = false, var isUnique: Boolean = false)

//Index
//Unique
//ForeignKey

interface IDBColumn<KOTLINDATATYPE> : ReadWriteProperty<IDBEntity, KOTLINDATATYPE> {
    var Table: IDBTable
    var ColumnName: String
    var NotNull: Boolean
    var DataType: IDataType<KOTLINDATATYPE>
    var AutoIncrement: AutoIncrementProperty
    var Unique: UniqueProperty
    var ForeignKey: IDBColumn<*>?
    var Index: IndexProperty
    var Comment: String
}
var IDBColumn<*>.IsPrimaryKey: Boolean
get() = Table.PrimaryKey.contains(this)
set(value) {
    if (value) {
        if (!Table.PrimaryKey.contains(this)) Table.PrimaryKey = arrayOf(*Table.PrimaryKey, this)
    }
    else
        if (Table.PrimaryKey.contains(this)) Table.PrimaryKey = arrayOf(*Table.PrimaryKey.filter { it != this }.toTypedArray())
}

fun <T> IDBColumn<T>.getDefaultValue() : T = DataType.DefaultValue
fun <T> IDBColumn<T>.setDefaultValue(value: T){
    DataType.DefaultValue = value
}
fun <T> IDBColumn<T>.isUnsigned() = if (DataType is IDBNumberType) (DataType as IDBNumberType).Unsigned else false