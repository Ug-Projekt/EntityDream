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


infix fun <T> IDBColumn<T>.equals(value: T): Equal = Equal({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.equals(column: IDBColumn<T>): Equal = Equal({ fullColumnName }, { column })
infix fun <T> IDBColumn<T>.notEquals(value: T): NotEqual = NotEqual({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.notEquals(column: IDBColumn<T>): NotEqual = NotEqual({ fullColumnName }, { column })
infix fun <T> IDBColumn<T>.greater(value: T): GreaterThen = GreaterThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.greater(column: IDBColumn<T>): GreaterThen = GreaterThen({ fullColumnName }, { column })
infix fun <T> IDBColumn<T>.greaterOrEqual(value: T): GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.greaterOrEqual(column: IDBColumn<T>): GreaterOrEqualThen = GreaterOrEqualThen({ fullColumnName }, { column})
infix fun <T> IDBColumn<T>.less(value: T): LessThen = LessThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.less(column: IDBColumn<T>): LessThen = LessThen({ fullColumnName }, { column })
infix fun <T> IDBColumn<T>.lessOrEqual(value: T): LessOrEqualThen = LessOrEqualThen({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.lessOrEqual(column: IDBColumn<T>): LessOrEqualThen = LessOrEqualThen({ fullColumnName }, { column })
/****************************Begin Like Exttended*********************************/
infix fun <T> IDBColumn<T>.like(value: T): Like = Like({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.like(column: IDBColumn<T>): Like = Like({ fullColumnName }, { column })
infix fun <T> IDBColumn<T>.notLike(value: T): NotLike = NotLike({ fullColumnName }, { value as Any })
infix fun <T> IDBColumn<T>.notLike(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { column })

infix fun <T> IDBColumn<T>.startsWith(value: T): Like = Like({ fullColumnName }, { "$value%" })
infix fun <T> IDBColumn<T>.startsWith(column: IDBColumn<T>): Like = Like({ fullColumnName }, { ColumnParameter("", column, "%") })
infix fun <T> IDBColumn<T>.notStartsWith(value: T): NotLike = NotLike({ fullColumnName }, { "$value%" })
infix fun <T> IDBColumn<T>.notStartsWith(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { ColumnParameter("", column, "%") })
infix fun <T> IDBColumn<T>.endsWith(value: T): Like = Like({ fullColumnName }, { "%$value" })
infix fun <T> IDBColumn<T>.endsWith(column: IDBColumn<T>): Like = Like({ fullColumnName }, { ColumnParameter("%", column, "") })
infix fun <T> IDBColumn<T>.notEndsWith(value: T): NotLike = NotLike({ fullColumnName }, { "%$value" })
infix fun <T> IDBColumn<T>.notEndsWith(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { ColumnParameter("%", column, "") })
infix fun <T> IDBColumn<T>.contains(value: T): Like = Like({ fullColumnName }, { "%$value%" })
infix fun <T> IDBColumn<T>.contains(column: IDBColumn<T>): Like = Like({ fullColumnName }, { ColumnParameter("%", column, "%") })
infix fun <T> IDBColumn<T>.notContains(value: T): NotLike = NotLike({ fullColumnName }, { "%$value%" })
infix fun <T> IDBColumn<T>.notContains(column: IDBColumn<T>): NotLike = NotLike({ fullColumnName }, { ColumnParameter("%", column, "%") })
/****************************End Like Exttended*********************************/
data class BetWeenPairs<T>(var from: T, var to: T)

infix fun <T> T.to(value: T): BetWeenPairs<T> = BetWeenPairs(this, value)
infix fun <T> IDBColumn<T>.between(value: IntRange): Between = Between({ fullColumnName }, { value.first }, { value.last })
fun <T> IDBColumn<T>.between(firstColumn: IDBColumn<T>, lastColumn: IDBColumn<T>): Between = Between({ fullColumnName }, { firstColumn }, { lastColumn})
infix fun <T> IDBColumn<T>.notBetween(value: IntRange): NotBetween = NotBetween({ fullColumnName }, { value.first }, { value.last})
fun <T> IDBColumn<T>.notBetween(firstColumn: IDBColumn<T>, lastColumn: IDBColumn<T>): NotBetween = NotBetween({ fullColumnName }, { firstColumn }, { lastColumn })
/*************************************************************************/
fun <T> IDBColumn<T>.iN(vararg values: T): In = In({ fullColumnName }, { ArrayList<Any>().apply { addAll(values.map { it as Any }) }.toTypedArray() })
fun <T> IDBColumn<T>.iN(vararg columns: IDBColumn<T>): In = In({ fullColumnName }, { ArrayList<Any>().apply { addAll(columns.map { it }) }.toTypedArray() })
fun <T> IDBColumn<T>.notiN(vararg values: T): NotIn = NotIn({ fullColumnName }, { ArrayList<Any>().apply { addAll(values.map { it as Any }) }.toTypedArray() })
fun <T> IDBColumn<T>.notiN(vararg columns: IDBColumn<T>): NotIn = NotIn({ fullColumnName }, { ArrayList<Any>().apply { addAll(columns.map { it }) }.toTypedArray() })

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