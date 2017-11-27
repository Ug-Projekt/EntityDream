/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 2:37 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult

data class TranslationSubject(var expression: IQueryExpression, var translationResult: TranslateResult? = null) : IPipeLineSubject {
    override val operationName: String = "Translate Common DML Objects to SQL Query"
    override val operationDescription: String = "Translate Common DML Objects to SQL Query"
}