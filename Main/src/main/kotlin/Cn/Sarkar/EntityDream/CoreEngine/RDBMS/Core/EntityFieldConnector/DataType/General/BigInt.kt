package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class BigInt(override var DefaultValue: Long, override var Unsigned: Boolean = false) : IDBPlainDataType<Long>, IDBNumberType {
    override var Name: String = "BigInt"
}