package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.Aggregate

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.IDBAggregateFunction

class Min(var Value: () -> Any) : IDBAggregateFunction {
    override val Name: String = "MAX"
}