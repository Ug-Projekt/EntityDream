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

class GenerateUpdateTaskSubject(val entity: IDBEntity, var values: KeyValuePair<String, Any>) : IPipeLineSubject {
    override val operationName: String = "Generate UpdateTask, add to task queue"
    override val operationDescription: String = "يىڭىدىن يىڭىلاش ۋەزىپىسى ھاسىل قىلىپ تىزىملىككە "
}