package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import kotlin.Float

class Float(override var DefaultValue: Float = 0f, override var Unsigned: Boolean = false) : IDBPlainDataType<Float>, IDBNumberType {
    override var Name: String = "Float"
}