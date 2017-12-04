package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import kotlin.Float

class Decimal(override var DefaultValue: Float = 0f, override var Unsigned: Boolean = false) : IDBPlainDataType<Float>, IDBNumberType<Float> {
    override var Name: String = "Decimal"
}