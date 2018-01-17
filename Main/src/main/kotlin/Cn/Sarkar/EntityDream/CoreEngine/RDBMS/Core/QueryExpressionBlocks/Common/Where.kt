/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:27 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SuperBlock

interface WhereItemCondition

class And(vararg conditions: WhereItemCondition) : WhereItemCondition //ھەمدە
{
    var conditions: ArrayList<WhereItemCondition> = ArrayList()
    init {
        this.conditions.addAll(conditions)
    }
}
class Or(vararg conditions: WhereItemCondition) : WhereItemCondition  //ياكى
{
    var conditions: ArrayList<WhereItemCondition> = ArrayList()
    init {
        this.conditions.addAll(conditions)
    }
}

class Equal(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition //تەڭ
class NotEqual(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition //تەڭ ئەمەس
class GreaterThen(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition  //چوڭ
class LessThen(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition //كىچىك
class GreaterOrEqualThen(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition //چوڭ ياكى تەڭ
class LessOrEqualThen(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition //چوڭ ياكى كىچىك
class Between(var first: () -> String, var from: () -> SqlParameter, var to: () -> SqlParameter) : WhereItemCondition  //ئارىلىقىدا
class NotBetween(var first: () -> String, var from: () -> SqlParameter, var to: () -> SqlParameter) : WhereItemCondition  //ئارىلىقىدا ئەمەس
class Like(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition  //Like
class NotLike(var first: () -> String, var last: () -> SqlParameter) : WhereItemCondition  //Not Like
class In(var first: () -> String, var others: () -> Array<SqlParameter>) : WhereItemCondition // ئىچىدە
class NotIn(var first: () -> String, var others: () -> Array<SqlParameter>) : WhereItemCondition // ئىچىدە ئەمەس

class Where(
        var condition: WhereItemCondition
) : SuperBlock {

}

data class ColumnParameter(var prefix: String, var column: IDBColumn<*>, var postfix: String)
{
    override fun toString(): String = "${if (prefix != "") prefix else ""}${column.fullColumnName}${if (postfix != "") postfix else ""}"
}