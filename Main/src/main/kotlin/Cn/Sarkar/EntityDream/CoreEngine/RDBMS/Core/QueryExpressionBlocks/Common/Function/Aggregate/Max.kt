/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 5:28 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBAggregateFunction

class Max(var Value: () -> Any) : IDBAggregateFunction {
    override val Name: String = "MAX"
}