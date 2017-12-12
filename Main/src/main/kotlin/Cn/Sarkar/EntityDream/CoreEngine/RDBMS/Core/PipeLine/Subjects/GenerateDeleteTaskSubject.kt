/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 8:15 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.KeyValuePair
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where

class GenerateDeleteTaskSubject(val entity: IDBEntity, var where: Where) : IPipeLineSubject {
    override val operationName: String = "Generate DeleteTask, add to task queue"
    override val operationDescription: String = "يىڭىدىن ئۆچۈرۈش ۋەزىپىسى ھاسىل قىلىپ تىزىملىككە "
}