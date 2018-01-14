/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 1:55 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

data class TranslateResult(var md5Key: String, var sqlQuery: String, var parameters: Collection<Any>, var fullSqlQuery: String, val expression: IQueryExpression)

interface IQueryTranslator {
    val DataContext: IDataContext
    fun Translate(expression: IQueryExpression) : TranslateResult
}
