package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class MediumInt(override var DefaultValue: kotlin.Int = 0, override var Unsigned: Boolean = false) : IDBPlainDataType<kotlin.Int>, IDBNumberType {
    override var Name: String = "MediumInt"
}