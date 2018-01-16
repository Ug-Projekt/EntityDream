/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/23/17
Time: 7:28 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShip.RelationShipOneFieldConnector
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

data class GetEntityRelationShipOneFieldSubject<KOTLINDATATYPE : IDBEntity>(
        val relationShipColumn: IDBColumn<*>,
        var delegateConnector: RelationShipOneFieldConnector<*, KOTLINDATATYPE>,
        var entityGenerator: (context: IDataContext) -> KOTLINDATATYPE,
        val thisRefEntity: IDBEntity,
        var selectResult: IDBEntity? = null
) : IPipeLineSubject {
    override val Name: String = "Get Entity RelationShip Mapped Field."
    override val Description: String = "Get Entity RelationShip Mapped Field."
}


