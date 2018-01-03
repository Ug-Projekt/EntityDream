/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/23/17
Time: 11:56 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShip

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityRelationShipManyFieldSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class RelationShipManyFieldConnector<ENTITY: IDBEntity, KOTLINDATATYPE: IQueriableCollection<*>>(private val relationShipColumn: IDBColumn<*>, private val entityGenerator: (context: IDataContext) -> ENTITY) : ReadOnlyProperty<ENTITY, KOTLINDATATYPE> {

    override fun getValue(thisRef: ENTITY, property: KProperty<*>): KOTLINDATATYPE {
        val cpl = thisRef.DataContext.clonedPipeLine
        val result = thisRef.DataContext.execute(cpl, GetEntityRelationShipManyFieldSubject(relationShipColumn, this as RelationShipManyFieldConnector<*, IQueriableCollection<*>>, entityGenerator, thisRef, null))
        return result.selectResult as? KOTLINDATATYPE ?: throw Exception("قايتۇرماقچى بولغان قىممەتنى ئىشلەتكىلى بولمايدۇ، قىممەت قۇرۇق بولماسلىقى كىرەك،Result is null, result not available, 返回值不允许Null值。")
    }
}


