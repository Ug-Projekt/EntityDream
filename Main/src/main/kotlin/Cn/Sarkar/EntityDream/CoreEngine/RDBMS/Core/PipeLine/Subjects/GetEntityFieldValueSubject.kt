/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 5:27 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class GetEntityFieldValueSubject(val column: IDBColumn<*>, val entity: IDBEntity, var value: Any) : IPipeLineSubject {
    override val Name: String = "Entity Field Getter"
    override val Description: String = "Entity Field And ValuesCache Connector"
}