package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBAggregateFunction

class Sum(var Value: () -> Any) : IDBAggregateFunction {
    override val Name: String = "SUM"
}