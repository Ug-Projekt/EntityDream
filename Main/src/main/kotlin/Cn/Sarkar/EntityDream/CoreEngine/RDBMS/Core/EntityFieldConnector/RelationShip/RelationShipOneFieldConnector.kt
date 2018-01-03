/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/23/17
Time: 3:04 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShip

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeSelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityRelationShipOneFieldSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.SetEntityRelationShipOneFieldSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RelationShipOneFieldConnector<ENTITY: IDBEntity, KOTLINDATATYPE: IDBEntity>(private val relationShipColumn: IDBColumn<*>, private val entityGenerator: (context: IDataContext) -> KOTLINDATATYPE) : ReadWriteProperty<ENTITY, KOTLINDATATYPE> {
    override fun getValue(thisRef: ENTITY, property: KProperty<*>): KOTLINDATATYPE {
        val cpl = thisRef.DataContext.clonedPipeLine
        val result = thisRef.DataContext.execute(cpl, GetEntityRelationShipOneFieldSubject(relationShipColumn,this, { entityGenerator(thisRef.DataContext) }, thisRef, null))
        return result.selectResult as? KOTLINDATATYPE ?: throw Exception("قايتۇرماقچى بولغان قىممەتنى ئىشلەتكىلى بولمايدۇ، قىممەت قۇرۇق بولماسلىقى كىرەك،Result is null, result not available, 返回值不允许Null值。")
    }

    internal var cachedValue: IDBEntity? = null

    override fun setValue(thisRef: ENTITY, property: KProperty<*>, value: KOTLINDATATYPE) {
        val cpl = thisRef.DataContext.clonedPipeLine
        val subject = SetEntityRelationShipOneFieldSubject(relationShipColumn, this, thisRef, (value as IDBEntity).values!![relationShipColumn.ForeignKey!!]!!)
        val result0 = thisRef.DataContext.execute(cpl, subject)
    }
}