/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/13/18
Time: 6:01 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class TypeAdapterSubject(var Input: IDBDataType<*>) : IPipeLineSubject {
    override val operationName: String = "Kotlin Data Type And DataBase(MySql, SqlServer, SqLite, Oracle ...) Data Type Adapter"
    override val operationDescription: String = "Kotlin Data Type And Each DataBase DataType Adapter"
    var OutPutString: String? = null
}