/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 2:37 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult

data class TranslationSubject(var expression: IQueryExpression, var translationResult: TranslateResult? = null) : IPipeLineSubject {
    override val Name: String = "Translate Common DML Objects to SQL Query"
    override val Description: String = "Translate Common DML Objects to SQL Query"
}