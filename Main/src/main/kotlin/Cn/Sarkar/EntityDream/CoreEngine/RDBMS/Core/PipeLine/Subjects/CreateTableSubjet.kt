/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/11/18
Time: 12:20 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.CreateTable.CreateTableExpression

class CreateTableSubjet(vararg expressions: CreateTableExpression) : IPipeLineSubject {
    val expressions = expressions
    override val Name: String = "CreateTableSubject"
    override val Description: String = "Craate a new table"
    var exception: Exception? = null
    var size: Int = 0
}