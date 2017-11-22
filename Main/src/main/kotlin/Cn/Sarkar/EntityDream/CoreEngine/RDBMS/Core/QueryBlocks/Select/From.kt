/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:08 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.SuperBlock

interface FromSelector

class FromQueryContainer(var select: SelectQueryExpression, var aliasName: String) : FromSelector
class FromTable(var tableName: String) : FromSelector

enum class JoinMode{ InnerJoin, RightJoin, LeftJoin, FullJoin }
class Join(var joinMode: JoinMode = JoinMode.InnerJoin, var fromSelector: FromSelector, var on: WhereItemCondition)

class From(var fromSelector: FromSelector, vararg join: Join) : SuperBlock {
    var join: ArrayList<Join> = ArrayList()
    init {
        this.join.addAll(join)
    }
}