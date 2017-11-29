/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 4:53 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult

data class ParameterContent(var uniqueMd5Key: String, var parameters: Array<Any>)
data class QueryGroup(
        var query: TranslateResult,
        var content: MutableList<ParameterContent> = ArrayList()
)

data class QueryGroupSubject(val groups: HashMap<String, QueryGroup> = HashMap(), val translateResult: TranslateResult) : IPipeLineSubject {
    override val operationName: String = "Group SQL Query"
    override val operationDescription: String = "ئوخشاش قۇرۇلمىدىكى SQL جۈملىرىنى گورۇپپىلايدۇ"
}

