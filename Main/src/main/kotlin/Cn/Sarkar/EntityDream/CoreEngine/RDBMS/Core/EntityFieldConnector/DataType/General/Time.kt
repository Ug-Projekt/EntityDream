package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import org.joda.time.DateTime
import java.sql.Time

/**
 * Default datetime is my mom birthday,
 * I Love Mom.
 */

class Time(override var DefaultValue: Time = Time(DateTime(1964, 4, 6, 0, 0, 0).millis)) : IDBPlainDataType<Time> {
    override var Name: String = "Time"
}