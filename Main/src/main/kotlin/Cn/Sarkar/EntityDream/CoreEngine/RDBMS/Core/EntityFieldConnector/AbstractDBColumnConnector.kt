/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 5:24 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityFieldValueSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.SetEntityFieldValueSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.getDefaultValue
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import kotlin.reflect.KProperty

abstract class AbstractDBColumnConnector<KOTLINDATATYPE> : IDBColumn<KOTLINDATATYPE>
{
    protected fun setup(){
        this.Table.Columns = arrayOf(this, *Table.Columns)
    }

    override fun getValue(entity: IDBEntity, property: KProperty<*>): KOTLINDATATYPE {
        val cpl = entity.DataContext.clonedPipeLine
        val result = entity.DataContext.execute(cpl, GetEntityFieldValueSubject(this, entity, getDefaultValue() as Any))
        return result.value as KOTLINDATATYPE
    }

    override fun setValue(entity: IDBEntity, property: KProperty<*>, value: KOTLINDATATYPE) {
        val cpl = entity.DataContext.clonedPipeLine
        val result = entity.DataContext.execute(cpl, SetEntityFieldValueSubject(this, entity, value as Any))
    }
}