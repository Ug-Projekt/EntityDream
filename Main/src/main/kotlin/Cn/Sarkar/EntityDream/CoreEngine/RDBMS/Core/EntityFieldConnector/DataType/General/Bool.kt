package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class Bool(override var DefaultValue: Boolean) : IDBPlainDataType<Boolean> {
    override var Name: String = "Bool"
}