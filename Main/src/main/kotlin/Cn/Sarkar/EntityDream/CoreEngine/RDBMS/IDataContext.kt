///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/14/17
//Time: 3:41 PM
// */
//
//package Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryTranslator
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Delete.DeleteQueryExpression
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Insert.InsertQueryExpression
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.UpdateQueryExpression
//import java.sql.Connection
//import java.sql.PreparedStatement
//import java.sql.Statement
//import java.sql.Timestamp
//import java.util.*
//import java.util.Date
//import kotlin.collections.HashMap
//
//fun PreparedStatement.WriteParameters(index: Int, value: Any){
//    when (value)
//    {
//        is String -> this.setString(index, value)
//        is Boolean -> this.setBoolean(index, value)
//        is Byte -> this.setByte(index, value)
//        is Short -> this.setShort(index, value)
//        is Int -> this.setInt(index, value)
//        is Long -> this.setLong(index, value)
//        is Float -> this.setFloat(index, value)
//        is Double -> this.setDouble(index, value)
////                is Decimal -> statement.setDouble(index, value)
//        is ByteArray -> this.setBytes(index, value)
//        is Date -> this.setDate(index, java.sql.Date(value.time))
//        is Timestamp -> this.setTimestamp(index, value)
//        else -> throw Exception("قوللىمايدىغان پارامىتىر تىپى...")
//    }
//}
//
//abstract class IDataContext {
//
//    open var updateTasks: HashMap<String, UpdateQueryExpression> = HashMap()
//    open var deleteTasks: HashMap<String, DeleteQueryExpression> = HashMap()
//    open var insertTasks: HashMap<String, Pair<InsertQueryExpression, IDBEntity>> = HashMap()
//    open var itemFactories: HashMap<Class<*>, (context: IDataContext) -> Any> = HashMap()
//
//    inline fun <reified T : IDBEntity>registerGenerator(noinline generator: (context: IDataContext) -> T){
//        itemFactories.put(T::class.java, generator as (context: IDataContext) -> Any)
//    }
//
//    inline fun <reified T : IDBEntity> generateNew() : T =  itemFactories[T::class.java]?.invoke(this) as T? ?: throw Exception("ئاۋال ھاسىللىغۇچ تىزىملاڭ!")
//
//    abstract fun queryTranslator(): IQueryTranslator
//    abstract var connection: Connection
//
//    open fun closeConnection(): Boolean {
//        connection.close()
//        return true
//    }
//
//    open fun saveChanges(): Int {
//
//        var retv = 0
//
//
//        class TaskContainer(var Query: String, var parameters: ArrayList<Pair<IDBEntity?, Collection<Any>>> = ArrayList())
//
//        val group = HashMap<String, TaskContainer>()
//
//        fun group(key: String, value: IQueryExpression, entity: IDBEntity?) {
//            val result = queryTranslator().Translate(value)
//            var containItem = group[result.md5Key]
//            if (containItem == null) {
//                containItem = TaskContainer(result.sqlQuery)
//                group.put(result.md5Key, containItem)
//            }
//
//            containItem.parameters.add(Pair(entity, result.parameters))
//        }
//
//
//
//        updateTasks.forEach { key, value -> group(key, value, null) }
//        deleteTasks.forEach { key, value -> group(key, value, null) }
//
//
////        connection.autoCommit = false
//        group.forEach { key, value ->
//            val statement = connection.prepareStatement(value.Query)
//            value.parameters.forEach {
//                it.second.forEachIndexed {
//                    index, any -> statement.WriteParameters(index + 1, any)
//                }
//                statement.addBatch()
//            }
//            retv += statement.executeBatch().sumBy { it }
//            statement.close()
//        }
//
//        group.clear()
//
//        insertTasks.forEach { key, value -> group(key, value.first, value.second) }
//
//        group.forEach { key, value ->
//            val statement = connection.prepareStatement(value.Query, Statement.RETURN_GENERATED_KEYS)
//            value.parameters.forEach {
//                it.second.forEachIndexed {
//                    index, any -> statement.WriteParameters(index + 1, any)
//                }
//                retv += statement.executeUpdate()
//                val idResult = statement.generatedKeys
//                if (idResult.next()) {
//                    it.first?.ID = idResult.getInt(1)
//                }
//                idResult.close()
//            }
//            statement.close()
//        }
//
//        deleteTasks.clear()
//        updateTasks.clear()
//        insertTasks.clear()
//
//        return retv
//    }
//}
