/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/27/17
Time: 4:05 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine

interface ExecutionResult

data class EffectedRows(var Count: Int) : ExecutionResult
//data class


class QueryExecutionSubject(var result: ExecutionResult) : IPipeLineSubject{
    override val operationName: String = "Execute SQL Query"
    override val operationDescription: String = "This is sql query execution operation"
    var groups: HashMap<String, QueryGroup> = HashMap()
}