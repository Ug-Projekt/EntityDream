package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class TinyInt(override var DefaultValue: Byte = 0, override var Unsigned: Boolean = false) : IDBPlainDataType<Byte>, IDBNumberType<Byte> {
    override var Name: String = "TinyInt"
}