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

data class SetEntityRelationShipOneFieldSubject<KOTLINDATATYPE : IDBEntity>(
        val relationShipColumn: IDBColumn<*>,
        var delegateConnector: RelationShipOneFieldConnector<*, KOTLINDATATYPE>,
        val thisRefEntity: IDBEntity,
        val value: Any
) : IPipeLineSubject {
    override val Name: String = "Set Entity RelationShip Mapped One Field."
    override val Description: String = "Set Entity RelationShip Mapped One Field."
}


