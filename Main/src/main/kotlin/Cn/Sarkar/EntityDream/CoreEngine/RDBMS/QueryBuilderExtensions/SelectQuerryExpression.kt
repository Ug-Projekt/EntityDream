/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 11:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.DBCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select.*

interface IExpressionBuilder<T>
class WhereXprBuilder<ENTITY : IDBEntity>(var expression: SelectQueryExpression) : IExpressionBuilder<ENTITY>
class SingleXprBuilder<ENTITY : IDBEntity>(var expression: SelectQueryExpression) : IExpressionBuilder<ENTITY>
class SingleOrDefaultXprBuilder<ENTITY : IDBEntity?>(var expression: SelectQueryExpression) : IExpressionBuilder<ENTITY>


val IDBColumn<*>.columnName: String
get() = "${this.Table.TableName}.${this.ColumnName}"

infix fun <T> IDBColumn<T>.equals(value: T) : Equal = Equal({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.notEquals(value: T) : NotEqual = NotEqual({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.greater(value: T) : GreaterThen = GreaterThen({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.greaterOrEqual(value: T) : GreaterOrEqualThen = GreaterOrEqualThen({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.less(value: T) : LessThen = LessThen({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.lessOrEqual(value: T) : LessOrEqualThen = LessOrEqualThen({columnName}, {value as Any})
infix fun <T> IDBColumn<T>.like(value: T) : Like = Like({columnName}, {value as Any})
/*************************************************************************/
data class BetWeenPairs<T>(var from: T, var to: T)
infix fun <T> T.to(value: T) : BetWeenPairs<T> = BetWeenPairs(this, value)
infix fun <T> IDBColumn<T>.between(value: BetWeenPairs<T>) : Between = Between({columnName}, {value.from as Any}, {value.to as Any})
/*************************************************************************/
fun <T> IDBColumn<T>.iN(vararg values: T) : In = In ({columnName}, { arrayOf(values)})

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


infix fun <T : IDBEntity> DBCollection<T>.where(condition: () -> WhereItemCondition) : WhereXprBuilder<T> =
        WhereXprBuilder(SelectQueryExpression(
                Select(
                        *(this.table.Columns.map { FromColumn(it.columnName)}.toTypedArray())
                ),
                From(FromTable(this.table.TableName))
        ))

infix fun <T : IDBEntity> DBCollection<T>.single(condition: () -> WhereItemCondition) : SingleXprBuilder<T> =
        SingleXprBuilder(SelectQueryExpression(
                Select(
                        *(this.table.Columns.map { FromColumn(it.columnName)}.toTypedArray())
                ),
                From(FromTable(this.table.TableName))
        ))

infix fun <T : IDBEntity> DBCollection<T>.singleOrDefault(condition: () -> WhereItemCondition) : SingleOrDefaultXprBuilder<T?> =
        SingleOrDefaultXprBuilder(SelectQueryExpression(
                Select(
                        *(this.table.Columns.map { FromColumn(it.columnName)}.toTypedArray())
                ),
                From(FromTable(this.table.TableName))
        ))
