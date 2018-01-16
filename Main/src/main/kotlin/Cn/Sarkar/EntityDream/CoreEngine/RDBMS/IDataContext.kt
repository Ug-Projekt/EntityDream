/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:41 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeInsertQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeUpdateQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.Pipeline.Extension.installFeature
import java.sql.Connection

fun <T : IPipeLineSubject> IDataContext.execute(clonedPipeLine: CorePipeLine, subject: T): T = clonedPipeLine.execute(this, subject) as T

/**
 *كلونلانغان يىڭى PipeLine ئوبىيكىتى
 */
internal val IDataContext.clonedPipeLine: CorePipeLine
    get() = CorePipeLine().apply {
        val oldPipeLine = this@clonedPipeLine.pipeLine
        oldPipeLine.getAllContainer().forEach {
            this[it.key].merge(it)
        }
    }

abstract class IDataContext : IQueryContext {
    override var updateTasks: HashMap<String, UpdateQueryExpression> = LinkedHashMap()
    override var deleteTasks: HashMap<String, DeleteQueryExpression> = LinkedHashMap()
    override var insertTasks: HashMap<String, InsertQueryExpression> = LinkedHashMap()
    val insertedIntities = HashMap<String, IDBEntity>()

//    open var itemFactories: HashMap<Class<*>, (context: IDataContext) -> Any> = HashMap()

    var pipeLine = CorePipeLine()

    abstract var connection: Connection

    open fun closeConnection(): Boolean {
        connection.close()
        return true
    }

    open fun saveChanges(): Int {
        var retv = 0
        val ids = executeInsertQuery()
        retv = ids.size
        retv += executeUpdateQuery().sum()

        ids.filter { it.id != -1L }.forEach {
            val entity = insertedIntities[it.uniqueMd5Key]!!
            val col = entity.Table.PrimaryKey.single { it.AutoIncrement.autoIncrement }
            entity.values!![col.ColumnName] = it.id
            entity.FromDB = true
        }

        insertedIntities.clear()
        insertTasks.clear()
        deleteTasks.clear()
        updateTasks.clear()
        return retv
    }

    init {
        pipeLine.installFeature(QueryGrouper)
        pipeLine.installFeature(QueryExecuter)
        pipeLine.installFeature(SelectResultReader)
        pipeLine.installFeature(DataReader)
        pipeLine.installFeature(InsertResultReader)
        pipeLine.installFeature(UpdateAndDeleteResultReader)
        pipeLine.installFeature(EntityFieldSetter)
        pipeLine.installFeature(EntityFieldGetter)
        pipeLine.installFeature(GenerateInsertTask)
        pipeLine.installFeature(GenerateUpdateTask)
        pipeLine.installFeature(GenerateDeleteTask)
        pipeLine.installFeature(RelationShipOneFieldGetter)
        pipeLine.installFeature(RelationShipOneFieldSetter)
        pipeLine.installFeature(RelationShipManyFieldGetter)
    }
}

///*Begin For Entity Generator*/
//inline fun <reified T : IDBEntity> IDataContext.registerGenerator(noinline generator: (context: IDataContext) -> T) {
//    itemFactories.put(T::class.java, generator as (context: IDataContext) -> Any)
//}
//
//inline fun <reified T : IDBEntity> IDataContext.generateNew(): T = itemFactories[T::class.java]?.invoke(this) as T? ?: throw Exception("ئاۋال ھاسىللىغۇچ تىزىملاڭ!")
///*End For Entity Generator*/