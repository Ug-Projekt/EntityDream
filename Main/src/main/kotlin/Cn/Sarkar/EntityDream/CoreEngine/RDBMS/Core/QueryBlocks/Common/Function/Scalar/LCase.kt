package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.Scalar

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.IDBScalarFunction

class LCase(var Value: () -> Any) : IDBScalarFunction {
    override val Name: String = "UCASE"
}