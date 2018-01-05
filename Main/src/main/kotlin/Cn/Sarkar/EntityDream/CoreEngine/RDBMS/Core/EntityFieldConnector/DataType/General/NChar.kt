/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/17/17
Time: 6:01 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainScaledDataType
import kotlin.Int

class NChar(size: Int, override var DefaultValue: String = "") : IDBPlainScaledDataType<String> {
    override var ScaleValue: kotlin.Int = size
    override var Name: String = "NCHAR"
    override fun toString(): String = "NCHAR($ScaleValue)"

    init {
        if (size > 255 || size < 1) throw Exception("Out of range, range must be 1~155")
    }
}