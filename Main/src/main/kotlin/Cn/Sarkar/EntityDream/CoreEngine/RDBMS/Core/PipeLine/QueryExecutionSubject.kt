/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/27/17
Time: 4:05 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine

import java.sql.ResultSet

interface ExecutionResult

data class EffectedRows(var Count: Int) : ExecutionResult
data class RedirectedResult(var result: ResultSet)


class QueryExecutionSubject(var result: ExecutionResult? = null) : IPipeLineSubject{
    override val operationName: String = "Execute SQL Query"
    override val operationDescription: String = "This is sql query execution operation"
    var groups: HashMap<String, QueryGroup> = HashMap()
}