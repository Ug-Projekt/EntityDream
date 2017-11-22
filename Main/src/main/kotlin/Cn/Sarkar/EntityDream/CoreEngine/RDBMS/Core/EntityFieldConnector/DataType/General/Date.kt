package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import org.joda.time.DateTime

/**
 * Default datetime is my mom birthday,
 * I Love Mom.
 */
class Date(override var DefaultValue: DateTime = DateTime(1964, 4, 6, 0, 0, 0)) : IDBPlainDataType<DateTime> {
    override var Name: String = "Float"
}