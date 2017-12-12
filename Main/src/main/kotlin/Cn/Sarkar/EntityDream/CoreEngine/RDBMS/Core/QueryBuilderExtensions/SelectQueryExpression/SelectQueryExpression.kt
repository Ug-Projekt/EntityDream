/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 11:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeSelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBParameterizedFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute


val IDBColumn<*>.fullColumnName: String
    get() = "${this.Table.TableName}.${this.ColumnName}"


/*************************************************************************/
val IDBTable.SelectAllColumns: Select get() = Select(*(this.Columns.map { FromColumn(it.fullColumnName, DefaultValue = it.DataType.DefaultValue!!, AliasName = it.ColumnName) }.toTypedArray()))
val IDBTable.FromThis: From get() = From(FromTable(TableName))
val IDBTable.SelectQuery: ISelectQueryExpression get() = SelectQueryExpression(this, SelectAllColumns, FromThis)
/*************************************************************************/
val IDBTable.FromColumns: Array<FromColumn> get() = arrayOf(*(this.Columns.map { FromColumn(it.ColumnName, DefaultValue = it.DataType.DefaultValue!!) }.toTypedArray()))

/*************************************************************************/

private fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.applyUpdate(applyAction: COLLECTION.() -> Unit): COLLECTION where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression {

    if (this.Level > 0) {
        this.apply(applyAction)
        cached = false
        Level++
        return this
    }
    val qc = QueriableCollection<ENTITY>(this.Context, this.table, this.Where?.condition, ItemGenerator)

    qc.Select = clonedSelect
    qc.From = clonedFrom
    qc.GroupBy = clonedGroupBy
    qc.GroupBy = clonedGroupBy
    qc.Having = clonedHaving
    qc.OrderBy = clonedOrderby
    qc.Level = Level + 1

    (qc as COLLECTION).applyAction()

    return qc
}

infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.where(condition: () -> WhereItemCondition) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = applyUpdate { if (this.Where == null) this.Where = Where(condition()) else this.Where!!.condition = condition() }

infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.andWhere(condition: (table: IDBTable) -> WhereItemCondition) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = applyUpdate { Where?.condition = Where!!.condition and condition(this.table) }
infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.orWhere(condition: (table: IDBTable) -> WhereItemCondition) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = applyUpdate { Where?.condition = Where!!.condition or condition(this.table) }
fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.slice(vararg columns: IDBColumn<*>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.apply { this.Select.selectors.clear(); this.Select.selectors.addAll(columns.map { FromColumn(it.fullColumnName, DefaultValue = it.DataType.DefaultValue!!) }) }
internal fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.removeFromColumnSelectorIfExists(column: IDBColumn<*>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression {

    val col = this.Select.selectors.firstOrNull { it is FromColumn && it.SourceColumnName == column.fullColumnName } as? FromColumn
    if (col != null) this.Select.selectors.remove(col)
}

/*************************************************************************/
private fun IDBColumn<*>.generateFunction(select: Select, function: IDBParameterizedFunction, defaultValue: Any = this.DataType.DefaultValue!!): FromFunction {
    var fn: FromFunction
    val existingFromFunction = select.selectors.singleOrNull { it is FromFunction && it.AliasName == this.ColumnName } as? FromFunction
    if (existingFromFunction == null)
        fn = FromFunction(function, ColumnName, defaultValue)
    else {
        select.selectors.remove(existingFromFunction)
        fn = FromFunction(
                function.apply { this.Value = FuncFromFunction({ existingFromFunction.Function }) },
                ColumnName,
                defaultValue
        )
    }
    return fn

}

/*Aggregate Functions*/
infix fun <T : Number, ENTITY : IDBEntity, COLLECTION> COLLECTION.sum(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate {
    slice()
//    removeFromColumnSelectorIfExists(column)
    this.Select.selectors.add(column.generateFunction(Select, Sum(FuncFromColumn({ column.fullColumnName })), 0f))
}.run { Context.executeSelectQuery(this)[0][column] as T }

infix fun <T: Number, ENTITY : IDBEntity, COLLECTION> COLLECTION.avg(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate {
    slice()
//    removeFromColumnSelectorIfExists(column)
    this.Select.selectors.add(column.generateFunction(Select, Avg(FuncFromColumn({ column.fullColumnName }))))
}.run { Context.executeSelectQuery(this)[0][column] as T }

fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.count(column: IDBColumn<T>, distinct: Boolean = false) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate {
    slice()
//    removeFromColumnSelectorIfExists(column)
    this.Select.selectors.add(column.generateFunction(Select, Count(FuncFromColumn({ column.fullColumnName }), distinct), 0))
}.run { Context.executeSelectQuery(this)[0][column] as Int }

infix fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.count(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = count(column, false)
infix fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.countDistinct(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = count(column, true)

infix fun <T : Number, ENTITY : IDBEntity, COLLECTION> COLLECTION.max(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Max(FuncFromColumn({ column.fullColumnName })))) }
infix fun <T : Number, ENTITY : IDBEntity, COLLECTION> COLLECTION.min(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Min(FuncFromColumn({ column.fullColumnName })))) }
/*Scalar Functions*/
infix fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.lCase(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, LCase(FuncFromColumn({ column.fullColumnName })))) }

infix fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.uCase(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, UCase(FuncFromColumn({ column.fullColumnName })))) }
infix fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.len(column: IDBColumn<T>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Len(FuncFromColumn({ column.fullColumnName })))) }
fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.format(column: IDBColumn<T>, format: String) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Format(FuncFromColumn({ column.fullColumnName }), format))) }
fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.mid(column: IDBColumn<T>, from: Int, length: Int? = null) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Mid(FuncFromColumn({ column.fullColumnName }), from, length))) }
fun <T, ENTITY : IDBEntity, COLLECTION> COLLECTION.round(column: IDBColumn<T>, decimals: Float) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { removeFromColumnSelectorIfExists(column);this.Select.selectors.add(column.generateFunction(Select, Round(FuncFromColumn({ column.fullColumnName }), decimals))) }
/*************************************************************************/


infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.orderBy(column: IDBColumn<*>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { OrderBy = OrderBy(column.fullColumnName, Order.Asc) }

infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.orderByDesc(column: IDBColumn<*>) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { OrderBy = OrderBy(column.fullColumnName, Order.Desc) }
infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.take(number: Int) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { Select.top = number }


/*
Any
* */
infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.any(condition: () -> WhereItemCondition) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression, COLLECTION : Collection<ENTITY> = applyUpdate {
    if (this.Where == null) this.Where = Where(condition()) else this.Where!!.condition = condition()
}.run { size > 0 }

infix fun <ENTITY : IDBEntity, COLLECTION> COLLECTION.skip(number: Int) where COLLECTION : IQueriableCollection<ENTITY>, COLLECTION : ISelectQueryExpression = this.applyUpdate { Select.offset = number }