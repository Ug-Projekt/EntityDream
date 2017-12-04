/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:27 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SuperBlock

interface WhereItemCondition

class And(vararg conditions: WhereItemCondition) : Cloneable, WhereItemCondition //ھەمدە
{
    var conditions: ArrayList<WhereItemCondition> = ArrayList()
    init {
        this.conditions.addAll(conditions)
    }
}
class Or(vararg conditions: WhereItemCondition) : Cloneable, WhereItemCondition  //ياكى
{
    var conditions: ArrayList<WhereItemCondition> = ArrayList()
    init {
        this.conditions.addAll(conditions)
    }
}

class Equal(var first: () -> String, var last: () -> Any) : WhereItemCondition //تەڭ
class NotEqual(var first: () -> String, var last: () -> Any) : WhereItemCondition //تەڭ ئەمەس
class GreaterThen(var first: () -> String, var last: () -> Any) : WhereItemCondition  //چوڭ
class LessThen(var first: () -> String, var last: () -> Any) : WhereItemCondition //كىچىك
class GreaterOrEqualThen(var first: () -> String, var last: () -> Any) : WhereItemCondition //چوڭ ياكى تەڭ
class LessOrEqualThen(var first: () -> String, var last: () -> Any) : WhereItemCondition //چوڭ ياكى كىچىك
class Between(var first: () -> String, var from: () -> Any, var to: () -> Any) : WhereItemCondition  //ئارىلىقىدا
class Like(var first: () -> String, var last: () -> Any) : WhereItemCondition  //Like
class In(var first: () -> String, var others: () -> Array<Any>) : WhereItemCondition // ئىچىدە

class Where(
        var condition: WhereItemCondition
) : SuperBlock {

}