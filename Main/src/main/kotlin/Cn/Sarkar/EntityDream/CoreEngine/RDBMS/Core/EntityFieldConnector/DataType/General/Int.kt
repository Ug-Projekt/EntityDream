package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import kotlin.Int

class Int(override var DefaultValue: Int = 0, override var Unsigned: Boolean = false) : IDBPlainDataType<Int>, IDBNumberType {
    override var Name: String = "Int"
}