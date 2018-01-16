/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 8:30 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ValuesCache

class SelectResultSubject(var executionSubject: QueryExecutionSubject) : IPipeLineSubject {
    override val Name: String = "Get Select Result"
    override val Description: String = "Get select query execution result."
    var values: ValuesCache = ValuesCache()
}