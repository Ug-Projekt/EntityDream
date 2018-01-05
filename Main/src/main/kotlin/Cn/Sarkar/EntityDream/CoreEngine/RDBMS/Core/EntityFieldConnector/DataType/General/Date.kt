package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import org.joda.time.DateTime
import java.sql.Date

/**
 * Default datetime is my mom birthday,
 * I Love Mom.
 */
class Date(override var DefaultValue: Date = Date(DateTime(1964, 4, 6, 0, 0, 0).millis)) : IDBPlainDataType<Date> {
    override var Name: String = "Date"
}