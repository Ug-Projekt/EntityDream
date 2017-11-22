package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType

class SmallText(override var DefaultValue: String = "") : IDBPlainDataType<String> {
    override var Name: String = "SmallText"
}