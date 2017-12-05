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