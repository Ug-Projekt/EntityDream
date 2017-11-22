package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBNumberType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class SmallInt(override var DefaultValue: Short = 0, override var Unsigned: Boolean = false) : IDBPlainDataType<Short>, IDBNumberType {
    override var Name: String = "SmallInt"
}