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
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter

class GenerateEntityFieldUpdateTaskSubject(val entity: IDBEntity, var values: KeyValuePair<String, SqlParameter>) : IPipeLineSubject {
    override val Name: String = "Generate UpdateTask, add to task queue"
    override val Description: String = "يىڭىدىن يىڭىلاش ۋەزىپىسى ھاسىل قىلىپ تىزىملىككە قوشۇش"
}