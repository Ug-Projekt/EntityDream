package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainScaledDataType
import kotlin.Int

class NVarChar(size: kotlin.Int, override var DefaultValue: String = "") : IDBPlainScaledDataType<String> {
    override var ScaleValue: Int = size
    override var Name: String = "NVARCHAR"
    override fun toString(): String = "VARCHAR($ScaleValue)"

    init {
        if (size > 255 || size < 1) throw Exception("Out of range, range must be 1~155")
    }
}