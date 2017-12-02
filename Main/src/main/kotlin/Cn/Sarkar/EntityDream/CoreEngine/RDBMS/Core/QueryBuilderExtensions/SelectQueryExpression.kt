/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 11:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select.*

val IDBColumn<*>.fullColumnName: String
get() = "${this.Table.TableName}.${this.ColumnName}"

infix fun <T> IDBColumn<T>.equals(value: T) : Equal = Equal({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.notEquals(value: T) : NotEqual = NotEqual({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.greater(value: T) : GreaterThen = GreaterThen({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.greaterOrEqual(value: T) : GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.less(value: T) : LessThen = LessThen({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.lessOrEqual(value: T) : LessOrEqualThen = LessOrEqualThen({ fullColumnName }, {value as Any})
infix fun <T> IDBColumn<T>.like(value: T) : Like = Like({ fullColumnName }, {value as Any})
/*************************************************************************/
data class BetWeenPairs<T>(var from: T, var to: T)
infix fun <T> T.to(value: T) : BetWeenPairs<T> = BetWeenPairs(this, value)
infix fun <T> IDBColumn<T>.between(value: IntRange) : Between = Between({ fullColumnName }, {value.first as Any}, {value.last as Any})
/*************************************************************************/
fun <T> IDBColumn<T>.iN(vararg values: T) : In = In ({ fullColumnName }, {ArrayList<Any>().apply { addAll(values.map { it as Any }) }.toTypedArray()})

infix fun WhereItemCondition.and(condition: WhereItemCondition) : And =
        if (this is And)
            this.apply {conditions.add(condition)}
        else
            And(this, condition)

infix fun WhereItemCondition.or(condition: WhereItemCondition) : Or =
        if (this is Or)
            this.apply {conditions.add(condition)}
        else
            Or(this, condition)
val IDBTable.SelectAllColumns: Select get() = Select(*(this.Columns.map { FromColumn(it.fullColumnName)}.toTypedArray()))
val IDBTable.FromThis: From get() = From(FromTable(TableName))
val IDBTable.SelectQuery: SelectQueryExpression get() = SelectQueryExpression(this, SelectAllColumns, FromThis)
val IDBTable.FromColumns: Array<FromColumn> get() = arrayOf(*(this.Columns.map { FromColumn(it.ColumnName) }.toTypedArray()))
private fun <T> QueriableCollection<T>.generateSelectQueryExpression() : SelectQueryExpression = SelectQueryExpression(table, table.SelectAllColumns, From(FromTable(table.TableName)))
infix fun SelectQueryExpression.where(condition: () -> WhereItemCondition) : SelectQueryExpression = this.apply { Where = Where(condition()) }
infix fun SelectQueryExpression.andWhere(condition: () -> WhereItemCondition) : SelectQueryExpression = this.apply { Where!!.condition = Where!!.condition and condition() }
infix fun SelectQueryExpression.orWhere(condition: () -> WhereItemCondition) : SelectQueryExpression = this.apply { Where!!.condition = Where!!.condition or condition() }
fun SelectQueryExpression.slice(vararg columns: IDBColumn<*>) : SelectQueryExpression = this.apply { this.Select.selectors.clear(); this.Select.selectors.addAll(columns.map { FromColumn(it.fullColumnName) }) }
internal fun SelectQueryExpression.removeFromColumnIfExists(column: IDBColumn<*>){
    val col = this.Select.selectors.firstOrNull { it is FromColumn && it.SourceName == column.fullColumnName } as FromColumn?
    if (col != null) this.Select.selectors.remove(col)
}
/*Aggregate Functions*/
infix fun <T> SelectQueryExpression.sum(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Sum({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.avg(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Avg({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.count(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Count({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.max(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Max({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.min(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Min({ column.fullColumnName }), column.ColumnName)) }
/*Scalar Functions*/
infix fun <T> SelectQueryExpression.lCase(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(LCase({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.uCase(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(UCase({ column.fullColumnName }), column.ColumnName)) }
infix fun <T> SelectQueryExpression.len(column: IDBColumn<T>) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Len({ column.fullColumnName }), column.ColumnName)) }
fun <T> SelectQueryExpression.format(column: IDBColumn<T>, format: String) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Format({ column.fullColumnName }, format), column.ColumnName)) }
fun <T> SelectQueryExpression.mid(column: IDBColumn<T>, from: Int, length: Int? = null) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Mid({ column.fullColumnName }, from, length), column.ColumnName)) }
fun <T> SelectQueryExpression.round(column: IDBColumn<T>, decimals: Float) : SelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(FromFunction(Round({ column.fullColumnName }, decimals), column.ColumnName)) }

