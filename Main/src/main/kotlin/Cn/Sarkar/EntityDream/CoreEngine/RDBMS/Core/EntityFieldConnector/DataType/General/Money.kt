package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import kotlin.Float

class Money(override var DefaultValue: kotlin.Float = 0f, override var Unsigned: Boolean = false) : IDBPlainDataType<kotlin.Float>, IDBNumberType<Float> {
    override var Name: String = "Money"
}