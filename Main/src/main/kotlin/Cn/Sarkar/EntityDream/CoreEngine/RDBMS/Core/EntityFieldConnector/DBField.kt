/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:17 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector

//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.AbstractDBField
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBField
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Equal
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Where
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.QueryBlocks.Select.*
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.readResult
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.WriteParameters
//import kotlin.reflect.KProperty
//
data class KeyValuePair<K, V>(var key: K, var value: V)
infix fun <K, V> K.to(value: V): KeyValuePair<K, V> = KeyValuePair(this, value)
//
//val IDBEntity.key: String
//get() = "Table:${this.TableName}:${this.ID}"
//
//open class DBField<T>(var entity: IDBEntity, var defaultValue: T, override var FieldName: String = IDBField.autoDetect, var whereUniqueCondition: KeyValuePair<String, Any> = IDBEntity.ID to IDBField.autoDetect, override var NotNull: Boolean, override var DataType: IDataType, override var AutoIncrement: Boolean, override var Unique: Boolean, override var PrimaryKey: Boolean, var convert: (source: Any) -> Any) : AbstractDBField<T>() {
//    override var Cache: T = defaultValue
//
//    override operator fun getValue(entity: IDBEntity, property: KProperty<*>) : T
//    {
//        detectFieldName(property)
//
//        if (this.Cache == defaultValue) {
//            autoDetectID(entity, whereUniqueCondition)
//            val expression = SelectQueryExpression(Select(FromColumn(FieldName)), From(FromTable(entity.TableName)), Where(Equal({whereUniqueCondition.key}, {whereUniqueCondition.value})))
//            val query = entity.DataContext.queryTranslator().Translate(expression)
//
//            val statement = entity.DataContext.connection.prepareStatement(query.sqlQuery)
//            query.parameters.forEachIndexed { index, any -> statement.WriteParameters(index + 1, any) }
//            val result = statement.executeQuery()
//            if (!result.next()) this.Cache = defaultValue else this.Cache = readResult(result, FieldName, defaultValue as Any) as T
//            result.close()
//            statement.close()
//        }
//
////        if (this.Cache == defaultValue) {
////            autoDetectID(entity, whereUniqueCondition)
////            val statement = entity.DataContext.connection.createStatement()
////            val query = "SELECT ${FieldName} FROM ${entity.TableName} WHERE ${whereUniqueCondition.key} = ${whereUniqueCondition.value};"
////            val result = statement.executeQuery(query)
////            if (!result.next()) this.Cache = defaultValue else this.Cache = readResult(result, FieldName, defaultValue as Any) as T
////            result.close()
////            statement.close()
////        }
//
//        return this.Cache as T
//    }
//
//    override operator fun setValue(entity: IDBEntity, property: KProperty<*>, value: T)
//    {
//        detectFieldName(property)
//        autoDetectID(entity, whereUniqueCondition)
//
//        this.Cache = value
//
//        //يىڭىلاشقا بولىدىغانلىق ئۇچۇرى ۋە يىڭىلاش ئۇچۇرى
//        createOrGetUpdateTask(entity, whereUniqueCondition).set.Values.add(FieldName to convert(value as Any))
//    }
//
//
//    init {
//        entity.Fields.add(this as IDBField<Any>)
//    }
//}
