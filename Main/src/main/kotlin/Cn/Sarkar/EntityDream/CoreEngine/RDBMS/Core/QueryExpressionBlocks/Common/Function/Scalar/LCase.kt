package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBScalarFunction

class LCase(var Value: () -> Any) : IDBScalarFunction {
    override val Name: String = "LCASE"
}