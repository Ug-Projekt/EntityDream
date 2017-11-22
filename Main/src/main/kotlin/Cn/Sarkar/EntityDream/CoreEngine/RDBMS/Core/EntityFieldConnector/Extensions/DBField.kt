///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/17/17
//Time: 8:05 PM
// */
//
//package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.Extensions
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.DBCollection
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.*
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShipConnector.ManyToOne
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShipConnector.OneToMany
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Equal
//import java.sql.Timestamp
//import java.util.Date
//
//typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int
//
//inline fun <reified T> generateDefaultValue() : T{
//    var default: Any = -1
//
//
//    default = if (0 is T) -1
//    else if ("" is T) ""
//    else if (0.toByte() is T) (-1).toByte()
//    else if (0f is T) -1f
//    else if (0.toLong() is T) (-1).toLong()
//    else if (0.toDouble() is T) (-1).toDouble()
//    else if ('n' is T) '-'
//    else if (byteArrayOf() is T) byteArrayOf()
//    else if (Date() is T) Date()
//    else if (Timestamp(System.currentTimeMillis()) is T) Timestamp(System.currentTimeMillis())
//    else if (0.toShort() is T) (-1).toShort()
//    else if (false is T) false
//    else if (Any() is T) Any()
//    else throw Exception("Please privide a default value.")
//
//    return default as T
//}
//
//fun generateDefaultType(value: Any) : IDataType = when(value){
//    is String -> VarChar(200)
//    is Boolean -> Bool()
//    is Byte -> TinyInt()
//    is Short -> MediumInt()
//    is Int -> DBInt()
//    is Long -> BigInt()
//    is Float -> Float()
//    is Double -> Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Double()
//    is Decimal -> Decimal()
//    is ByteArray -> Binary()
//    is Date -> Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Date()
//    is Timestamp -> TimeStamp()
//    else -> throw Exception("Please privide a default value.")
//}
//
//fun <T> IDBEntity.dbFieldWithNull(defaultType: IDataType = generateDefaultType(generateDefaultValue()), fieldName: String = IDBField.autoDetect, whereUniqueCondition: KeyValuePair<String, Any> = IDBEntity.ID to IDBField.autoDetect, isUnique: Boolean = false, isPrimaryKey: Boolean = false) : IDBField<T?>{
//    return DBField<T?>(this, null, fieldName, whereUniqueCondition, false, defaultType, false, isUnique, isPrimaryKey, {it})
//}
//
//fun <T> IDBEntity.dbField(defaultValue: T, defaultType: IDataType = generateDefaultType(defaultValue as Any), fieldName: String = IDBField.autoDetect, whereUniqueCondition: KeyValuePair<String, Any> = IDBEntity.ID to IDBField.autoDetect, isUnique: Boolean = false, isPrimaryKey: Boolean = false) : IDBField<T>{
//    return DBField<T>(this, defaultValue, fieldName, whereUniqueCondition, false, defaultType, false, isUnique, isPrimaryKey, {it})
//}
//
//fun IDBEntity.dbID(fieldName: String = IDBEntity.ID) : IDBField<Int> = DBIDField(this, fieldName)
//
//inline fun <reified T : IDBEntity> IDBEntity.manyToOne(DestTableField: String = IDBEntity.ID, noinline lookUpIfTheValue: () -> Any) : IDBField<T>{
//    return ManyToOne(DataContext.generateNew(), DestTableField, lookUpIfTheValue)
//}
//
//inline fun <reified ENTITY: IDBEntity> IDBEntity.oneToMany(DestTableField: String, noinline lookUpIfTheValue: () -> Any) : IDBField<DBCollection<ENTITY>> {
//    return OneToMany(DBCollection<ENTITY>(DataContext, IDBEntity.ID, Equal({ DestTableField }, lookUpIfTheValue)) { DataContext.generateNew() })
//}