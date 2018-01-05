/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 10:47 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util

import org.joda.time.DateTime
import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.sql.ResultSet
import java.sql.Time
import java.sql.Timestamp
import java.util.*

internal fun ResultSet.readResult(fieldName: String, defaultValue: Any?) : Any? = when(defaultValue){
    is String -> this.getString(fieldName)
    is Boolean -> this.getBoolean(fieldName)
    is Byte -> this.getByte(fieldName)
    is Short -> this.getShort(fieldName)
    is Int -> this.getInt(fieldName)
    is Long -> this.getLong(fieldName)
    is Float -> this.getFloat(fieldName)
    is Double -> this.getDouble(fieldName)
    is BigDecimal -> this.getBigDecimal(fieldName)
    is ByteArray -> this.getBytes(fieldName)
    is Date -> this.getDate(fieldName)
    is Time -> this.getTime(fieldName)
    is Timestamp -> this.getTimestamp(fieldName)
    is DateTime -> DateTime(this.getTimestamp(fieldName).time)
    is InputStream -> this.getBinaryStream(fieldName)
    is Reader -> this.getCharacterStream(fieldName)
    else -> this.getObject(fieldName)
} ?: null