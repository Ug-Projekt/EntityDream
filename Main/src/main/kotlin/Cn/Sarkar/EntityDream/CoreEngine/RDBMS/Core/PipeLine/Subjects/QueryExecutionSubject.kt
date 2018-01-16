/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/27/17
Time: 4:05 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import java.sql.Connection
import java.sql.PreparedStatement


class QueryExecutionSubject(var group: QueryGroup, var connection: Connection) : IPipeLineSubject {
    override val Name: String = "Execute SQL Query"
    override val Description: String = "This is sql query execution operation"
    var effectedRows: IntArray = IntArray(0, {0})
    var statement: PreparedStatement? = null
    var exception: Exception? = null
}








