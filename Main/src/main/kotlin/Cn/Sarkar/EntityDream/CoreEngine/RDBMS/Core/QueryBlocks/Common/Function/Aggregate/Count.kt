/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 5:24 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.Aggregate

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.IDBAggregateFunction

class Count(var Value: () -> Any, var Distinct: Boolean = false) : IDBAggregateFunction {
    override val Name: String = "COUNT"
}