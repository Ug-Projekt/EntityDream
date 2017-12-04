/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 11:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*

val IDBColumn<*>.fullColumnName: String
    get() = "${this.Table.TableName}.${this.ColumnName}"

infix fun <T> IDBColumn<T>.equals(value: T): Equal = Equal({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.notEquals(value: T): NotEqual = NotEqual({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.greater(value: T): GreaterThen = GreaterThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.greaterOrEqual(value: T): GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.less(value: T): LessThen = LessThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.lessOrEqual(value: T): LessOrEqualThen = LessOrEqualThen({ fullColumnName }, { value as Any })
/****************************Begin Like Exttended*********************************/
infix fun <T> IDBColumn<T>.like(value: T): Like = Like({ fullColumnName }, { value as Any })

infix fun <T> IDBColumn<T>.startsWith(value: T): Like = Like({ fullColumnName }, { "$value%" })
infix fun <T> IDBColumn<T>.endsWithWith(value: T): Like = Like({ fullColumnName }, { "%$value" })
infix fun <T> IDBColumn<T>.contains(value: T): Like = Like({ fullColumnName }, { "%$value%" })
/****************************End Like Exttended*********************************/
data class BetWeenPairs<T>(var from: T, var to: T)

infix fun <T> T.to(value: T): BetWeenPairs<T> = BetWeenPairs(this, value)
infix fun <T> IDBColumn<T>.between(value: IntRange): Between = Between({ fullColumnName }, { value.first as Any }, { value.last as Any })
/*************************************************************************/
fun <T> IDBColumn<T>.iN(vararg values: T): In = In({ fullColumnName }, { ArrayList<Any>().apply { addAll(values.map { it as Any }) }.toTypedArray() })

/*************************************************************************/
infix fun WhereItemCondition.and(condition: WhereItemCondition): And =
        if (this is And)
            this.apply { conditions.add(condition) }
        else
            And(this, condition)

/*************************************************************************/
infix fun WhereItemCondition.or(condition: WhereItemCondition): Or =
        if (this is Or)
            this.apply { conditions.add(condition) }
        else
            Or(this, condition)

/*************************************************************************/
val IDBTable.SelectAllColumns: Select get() = Select(*(this.Columns.map { FromColumn(it.fullColumnName, DefaultValue = it.DataType.DefaultValue!!, AliasName = it.ColumnName) }.toTypedArray()))
val IDBTable.FromThis: From get() = From(FromTable(TableName))
val IDBTable.SelectQuery: ISelectQueryExpression get() = SelectQueryExpression(this, SelectAllColumns, FromThis)
/*************************************************************************/
val IDBTable.FromColumns: Array<FromColumn> get() = arrayOf(*(this.Columns.map { FromColumn(it.ColumnName, DefaultValue = it.DataType.DefaultValue!!) }.toTypedArray()))

/*************************************************************************/
infix fun ISelectQueryExpression.where(condition: () -> WhereItemCondition): ISelectQueryExpression = this.apply { Where = Where(condition()) }

infix fun ISelectQueryExpression.andWhere(condition: () -> WhereItemCondition): ISelectQueryExpression = this.apply { Where!!.condition = Where!!.condition and condition() }
infix fun ISelectQueryExpression.orWhere(condition: () -> WhereItemCondition): ISelectQueryExpression = this.apply { Where!!.condition = Where!!.condition or condition() }
fun ISelectQueryExpression.slice(vararg columns: IDBColumn<*>): ISelectQueryExpression = this.apply { this.Select.selectors.clear(); this.Select.selectors.addAll(columns.map { FromColumn(it.fullColumnName, DefaultValue = it.DataType.DefaultValue!!) }) }
internal fun ISelectQueryExpression.removeFromColumnIfExists(column: IDBColumn<*>) {
    val col = this.Select.selectors.firstOrNull { it is FromColumn && it.SourceName == column.fullColumnName } as FromColumn?
    if (col != null) this.Select.selectors.remove(col)
}

/*************************************************************************/
private fun IDBColumn<*>.fromFunction(function: IDBFunction): FromFunction = FromFunction(function, ColumnName, this.DataType.DefaultValue!!)

/*Aggregate Functions*/
infix fun <T> ISelectQueryExpression.sum(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Sum({ column.fullColumnName }))) }

infix fun <T> ISelectQueryExpression.avg(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Avg({ column.fullColumnName }))) }
infix fun <T> ISelectQueryExpression.count(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Count({ column.fullColumnName }))) }
infix fun <T> ISelectQueryExpression.max(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Max({ column.fullColumnName }))) }
infix fun <T> ISelectQueryExpression.min(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Min({ column.fullColumnName }))) }
/*Scalar Functions*/
infix fun <T> ISelectQueryExpression.lCase(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(LCase({ column.fullColumnName }))) }

infix fun <T> ISelectQueryExpression.uCase(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(UCase({ column.fullColumnName }))) }
infix fun <T> ISelectQueryExpression.len(column: IDBColumn<T>): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Len({ column.fullColumnName }))) }
fun <T> ISelectQueryExpression.format(column: IDBColumn<T>, format: String): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Format({ column.fullColumnName }, format))) }
fun <T> ISelectQueryExpression.mid(column: IDBColumn<T>, from: Int, length: Int? = null): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Mid({ column.fullColumnName }, from, length))) }
fun <T> ISelectQueryExpression.round(column: IDBColumn<T>, decimals: Float): ISelectQueryExpression = this.apply { removeFromColumnIfExists(column);this.Select.selectors.add(column.fromFunction(Round({ column.fullColumnName }, decimals))) }
/*************************************************************************/
infix fun ISelectQueryExpression.orderBy(column: IDBColumn<*>): ISelectQueryExpression = this.apply { OrderBy = OrderBy(column.fullColumnName, Order.Asc) }

infix fun ISelectQueryExpression.orderByDesc(column: IDBColumn<*>): ISelectQueryExpression = this.apply { OrderBy = OrderBy(column.fullColumnName, Order.Desc) }
infix fun ISelectQueryExpression.take(number: Int): ISelectQueryExpression = this.apply { Select.top = number }
