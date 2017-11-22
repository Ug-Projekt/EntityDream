//package Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShipConnector
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.AbstractDBField
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.ORMRelationShip
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.IDBCollection
//import kotlin.reflect.KProperty
//
//class OneToMany<PROPERTY: IDBCollection>(defaultValue: PROPERTY) : AbstractDBField<PROPERTY>() {
//    override var FieldName: String = "RelationShip Field, Unnecessary!"
//    override var NotNull: Boolean = false
//    override var AutoIncrement: Boolean = false
//    override var Unique: Boolean = false
//    override var PrimaryKey: Boolean = false
//    override var DataType: IDataType = ORMRelationShip()
//
//    override var Cache: PROPERTY = defaultValue
//
//    override fun setValue(entity: IDBEntity, property: KProperty<*>, value: PROPERTY) {
//
//    }
//
//    override fun getValue(entity: IDBEntity, property: KProperty<*>): PROPERTY {
//        return Cache
//    }
//}