/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:41 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

fun PreparedStatement.WriteParameters(index: Int, value: Any){
    when (value)
    {
        is String -> this.setString(index, value)
        is Boolean -> this.setBoolean(index, value)
        is Byte -> this.setByte(index, value)
        is Short -> this.setShort(index, value)
        is Int -> this.setInt(index, value)
        is Long -> this.setLong(index, value)
        is Float -> this.setFloat(index, value)
        is Double -> this.setDouble(index, value)
//                is Decimal -> statement.setDouble(index, value)
        is ByteArray -> this.setBytes(index, value)
        is Date -> this.setDate(index, java.sql.Date(value.time))
        is Timestamp -> this.setTimestamp(index, value)
        else -> throw Exception("قوللىمايدىغان پارامىتىر تىپى...")
    }
}

abstract class IDataContext : IQueryContext {
    override var updateTasks: HashMap<String, UpdateQueryExpression> = LinkedHashMap()
    override var deleteTasks: HashMap<String, DeleteQueryExpression> = LinkedHashMap()
    override var insertTasks: HashMap<String, Pair<InsertQueryExpression, IDBEntity>> = LinkedHashMap()

    open var itemFactories: HashMap<Class<*>, (context: IDataContext) -> Any> = HashMap()

    var pipeLine = CorePipeLine()

    abstract var connection: Connection

    open fun closeConnection(): Boolean {
        connection.close()
        return true
    }

    open fun saveChanges(): Int {

        var retv = 0

        return retv
    }

    init {

    }
}

/*Begin For Entity Generate*/
inline fun <reified T : IDBEntity>IDataContext.registerGenerator(noinline generator: (context: IDataContext) -> T){
    itemFactories.put(T::class.java, generator as (context: IDataContext) -> Any)
}
inline fun <reified T : IDBEntity> IDataContext.generateNew() : T =  itemFactories[T::class.java]?.invoke(this) as T? ?: throw Exception("ئاۋال ھاسىللىغۇچ تىزىملاڭ!")
/*End For Entity Generate*/