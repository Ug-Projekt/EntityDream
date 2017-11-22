///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/18/17
//Time: 10:52 PM
// */
//
//package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBField
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.KeyValuePair
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.key
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Equal
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Where
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.Set
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.Update
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.UpdateQueryExpression
//import kotlin.reflect.KProperty
//
//abstract class AbstractDBField<PROPERTY> : IDBField<PROPERTY> {
//    open fun detectFieldName(property: KProperty<*>) = if (FieldName == IDBField.autoDetect) property.name.apply { FieldName = this } else FieldName
//
//    open fun createOrGetUpdateTask(entity: IDBEntity, whereUniqueCondition: KeyValuePair<String, Any>) : UpdateQueryExpression
//    {
//        var result = entity.DataContext.updateTasks[entity.key]
//        if (result == null)
//        {
//            result = UpdateQueryExpression(Update(entity.TableName), Set(), Where(Equal({whereUniqueCondition.key}, {whereUniqueCondition.value})))
//            entity.DataContext.updateTasks.put(entity.key, result)
//        }
//        return result!!
//    }
//    open fun autoDetectID(entity: IDBEntity, whereUniqueCondition: KeyValuePair<String, Any>){
//        if (whereUniqueCondition.value == IDBField.autoDetect) whereUniqueCondition.value = entity.ID
//    }
//}