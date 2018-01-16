/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/5/17
Time: 1:37 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.NormalParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter


infix fun <T> IDBColumn<T>.equals(value: T): Equal = Equal({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.equals(column: IDBColumn<T>): Equal = Equal({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.notEquals(value: T): NotEqual = NotEqual({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.notEquals(column: IDBColumn<T>): NotEqual = NotEqual({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.greater(value: T): GreaterThen = GreaterThen({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.greater(column: IDBColumn<T>): GreaterThen = GreaterThen({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.greaterOrEqual(value: T): GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.greaterOrEqual(column: IDBColumn<T>): GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.less(value: T): LessThen = LessThen({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.less(column: IDBColumn<T>): LessThen = LessThen({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.lessOrEqual(value: T): LessOrEqualThen = LessOrEqualThen({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.lessOrEqual(column: IDBColumn<T>): LessOrEqualThen = LessOrEqualThen({ fullColumnName }, { NormalParameter(column) })
/****************************Begin Like Exttended*********************************/
infix fun <T> IDBColumn<T>.like(value: T): Like = Like({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.like(column: IDBColumn<T>): Like = Like({ fullColumnName }, { NormalParameter(column) })
infix fun <T> IDBColumn<T>.notLike(value: T): NotLike = NotLike({ fullColumnName }, { MappedParameter(value as Any, DataType) })
infix fun <T> IDBColumn<T>.notLike(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { NormalParameter(column) })

infix fun <T> IDBColumn<T>.startsWith(value: T): Like = Like({ fullColumnName }, { MappedParameter("$value%", DataType) })
infix fun <T> IDBColumn<T>.startsWith(column: IDBColumn<T>): Like = Like({ fullColumnName }, { NormalParameter(ColumnParameter("", column, "%")) })
infix fun <T> IDBColumn<T>.notStartsWith(value: T): NotLike = NotLike({ fullColumnName }, { MappedParameter("$value%", DataType) })
infix fun <T> IDBColumn<T>.notStartsWith(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { NormalParameter(ColumnParameter("", column, "%")) })
infix fun <T> IDBColumn<T>.endsWith(value: T): Like = Like({ fullColumnName }, { MappedParameter("%$value", DataType) })
infix fun <T> IDBColumn<T>.endsWith(column: IDBColumn<T>): Like = Like({ fullColumnName }, { NormalParameter(ColumnParameter("%", column, "")) })
infix fun <T> IDBColumn<T>.notEndsWith(value: T): NotLike = NotLike({ fullColumnName }, { MappedParameter("%$value", DataType) })
infix fun <T> IDBColumn<T>.notEndsWith(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { NormalParameter(ColumnParameter("%", column, "")) })
infix fun <T> IDBColumn<T>.contains(value: T): Like = Like({ fullColumnName }, { MappedParameter("%$value%", DataType) })
infix fun <T> IDBColumn<T>.contains(column: IDBColumn<T>): Like = Like({ fullColumnName }, { NormalParameter(ColumnParameter("%", column, "%")) })
infix fun <T> IDBColumn<T>.notContains(value: T): NotLike = NotLike({ fullColumnName }, { MappedParameter("%$value%", DataType) })
infix fun <T> IDBColumn<T>.notContains(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { NormalParameter(ColumnParameter("%", column, "%")) })
/****************************End Like Exttended*********************************/
data class BetWeenPairs<T>(var from: T, var to: T)

infix fun <T> T.to(value: T): BetWeenPairs<T> = BetWeenPairs(this, value)
infix fun <T> IDBColumn<T>.between(value: IntRange): Between = Between({ fullColumnName }, { NormalParameter(value.first) }, { NormalParameter(value.last) })
fun <T> IDBColumn<T>.between(firstColumn: IDBColumn<T>, lastColumn: IDBColumn<T>): Between = Between({ fullColumnName }, { NormalParameter(firstColumn) }, { NormalParameter(lastColumn) })
infix fun <T> IDBColumn<T>.notBetween(value: IntRange): NotBetween = NotBetween({ fullColumnName }, { NormalParameter(value.first) }, { NormalParameter(value.last) })
fun <T> IDBColumn<T>.notBetween(firstColumn: IDBColumn<T>, lastColumn: IDBColumn<T>): NotBetween = NotBetween({ fullColumnName }, { NormalParameter(firstColumn) }, { NormalParameter(lastColumn) })
/*************************************************************************/
fun <T> IDBColumn<T>.iN(vararg values: T): In = In({ fullColumnName }, { ArrayList<SqlParameter>().apply { addAll(values.map { MappedParameter(it as Any, DataType) }) }.toTypedArray() })
fun <T> IDBColumn<T>.iN(vararg columns: IDBColumn<T>): In = In({ fullColumnName }, { ArrayList<SqlParameter>().apply { addAll(columns.map { NormalParameter(it) }) }.toTypedArray() })
fun <T> IDBColumn<T>.notiN(vararg values: T): NotIn = NotIn({ fullColumnName }, { ArrayList<SqlParameter>().apply { addAll(values.map { MappedParameter(it as Any, DataType) }) }.toTypedArray() })
fun <T> IDBColumn<T>.notiN(vararg columns: IDBColumn<T>): NotIn = NotIn({ fullColumnName }, { ArrayList<SqlParameter>().apply { addAll(columns.map { NormalParameter(it) }) }.toTypedArray() })

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