/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 10:47 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util

import java.io.InputStream
import java.io.Reader
import java.math.BigDecimal
import java.sql.ResultSet
import java.util.*

internal inline fun readResult(resultSet: ResultSet, fieldName: String, readType: Any) : Any? = when(readType){
    is String -> resultSet.getString(fieldName)
    is Boolean -> resultSet.getBoolean(fieldName)
    is Byte -> resultSet.getByte(fieldName)
    is Short -> resultSet.getShort(fieldName)
    is Int -> resultSet.getInt(fieldName)
    is Long -> resultSet.getLong(fieldName)
    is Float -> resultSet.getFloat(fieldName)
    is Double -> resultSet.getDouble(fieldName)
    is BigDecimal -> resultSet.getBigDecimal(fieldName)
    is ByteArray -> resultSet.getBytes(fieldName)
    is Date -> resultSet.getDate(fieldName)
    is InputStream -> resultSet.getBinaryStream(fieldName)
    is Any -> resultSet.getObject(fieldName)
    is Reader -> resultSet.getCharacterStream(fieldName)
    else -> null
}