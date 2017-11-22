///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/17/17
//Time: 8:22 PM
// */
//
//package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBField
//import kotlin.reflect.KProperty
//
//class DBIDField(entity: IDBEntity, override var FieldName: String = IDBEntity.ID) : IDBField<Int> {
//    override var Cache: Int = -1
//    override var NotNull: Boolean = true
//    override var DataType: IDataType = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int()
//    override var AutoIncrement: Boolean = true
//    override var Unique: Boolean = true
//    override var PrimaryKey: Boolean = true
//
//    override fun getValue(thisRef: IDBEntity, property: KProperty<*>): Int = Cache
//
//    override fun setValue(thisRef: IDBEntity, property: KProperty<*>, value: Int) {
//        Cache = value
//    }
//
//    init {
//        entity.Fields.add(this as IDBField<Any>)
//    }
//}