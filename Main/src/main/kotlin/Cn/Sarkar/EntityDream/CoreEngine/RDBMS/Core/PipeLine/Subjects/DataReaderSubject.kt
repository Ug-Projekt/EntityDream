/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 12:48 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import java.sql.ResultSet

class DataReaderSubject(val ResultSet: ResultSet, val FieldName: String, val DataType: IDBDataType<*>) : IPipeLineSubject {
    override val operationName: String = "Result Set Reader"
    override val operationDescription: String = "Result Set Reader"
    var Data: Any? = null
}