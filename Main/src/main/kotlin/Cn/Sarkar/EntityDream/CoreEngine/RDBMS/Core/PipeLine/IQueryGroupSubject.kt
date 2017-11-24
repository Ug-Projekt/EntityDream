/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 4:53 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity

data class QueryGroup(
        var md5Unique: String,
        var sqlQuery: String,
        var content: Array<Pair<Array<Any>, IDBEntity?>>
)

interface IQueryGroupSubject : IPipeLineSubject {

}