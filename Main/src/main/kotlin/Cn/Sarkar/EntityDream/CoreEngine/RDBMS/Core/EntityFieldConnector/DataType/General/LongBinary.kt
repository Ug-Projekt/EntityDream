package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class LongBinary(override var DefaultValue: ByteArray = byteArrayOf()) : IDBPlainDataType<ByteArray> {
    override var Name: String = "LongBinary"
}