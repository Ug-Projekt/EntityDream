///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/17/17
//Time: 1:12 PM
// */
//
//package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.DBCollection
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.AlwaysSelect
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.WhereItemCondition
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.IDataContext
//
//fun <T : IDBEntity> IDataContext.dbCollection(primaryKey: String = IDBEntity.ID, whereItemCondition: WhereItemCondition = AlwaysSelect(), itemGenerator: (context: IDataContext) -> T) : DBCollection<T> =
//        DBCollection<T>(this, primaryKey, whereItemCondition, {itemGenerator(this)})