/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/17/17
Time: 1:12 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute


fun <T : IDBEntity> IDataContext.defineTable(table: IDBTable, itemGenerator: (context: IDataContext) -> T) : QueriableCollection<T> =
        QueriableCollection(this, table, null, { itemGenerator(this@defineTable) })


/**
 * ئىزدەش جۈملىسى يۈرگۈزۈش
 */
fun IDataContext.executeSelectQuery(expression: ISelectQueryExpression): ValuesCache {
    val cpl = clonedPipeLine
    val result1 = execute(cpl, TranslationSubject(expression))
    val result2 = execute(cpl, QueryGroupSubject(translateResult = result1.translationResult!!))
    val result3 = execute(cpl, QueryExecutionSubject(result2.groups[result1.translationResult!!.md5Key]!!, this.connection))
    val result4 = execute(cpl, SelectResultSubject(result3))
    return result4.values
}

/**
 * يىڭىلاش ۋە ئۆچۈرۈش جۈملىسى يۈرگۈزۈش
 */
fun IDataContext.executeUpdateQuery(queryContext: IQueryContext = this): IntArray {
    val retv = ArrayList<Int>()

    val cpl = clonedPipeLine
    val groupSubject = QueryGroupSubject()

    fun IQueryExpression.groupQuerys(group: QueryGroupSubject) {
        val result = execute(cpl, TranslationSubject(this))
        group.translateResult = result.translationResult
        execute(cpl, group)
    }

    updateTasks.forEach { k, v -> v.groupQuerys(groupSubject) }
    deleteTasks.forEach { k, v -> v.groupQuerys(groupSubject) }

    groupSubject.groups.forEach { queryMd5Key, groupItem ->
        val result1 = execute(cpl, QueryExecutionSubject(groupItem, this.connection))
        val result2 = execute(cpl, UpdateAndDeleteResultSubject(result1))
        retv.add(result2.EffectedRows.sum())
    }

    return retv.toIntArray()
}

/**
 * قىستۇرۇش جۈملىسى يۈرگۈزۈش
 */
fun IDataContext.executeInsertQuery(queryContext: IQueryContext = this): IntArray {
    val retv = ArrayList<Int>()

    val cpl = clonedPipeLine
    val groupSubject = QueryGroupSubject()

    fun IQueryExpression.groupQuerys(group: QueryGroupSubject) {
        val result = execute(cpl, TranslationSubject(this))
        group.translateResult = result.translationResult
        execute(cpl, group)
    }

    insertTasks.forEach { k, v -> v.groupQuerys(groupSubject) }

    groupSubject.groups.forEach { queryMd5Key, groupItem ->
        val result1 = execute(cpl, QueryExecutionSubject(groupItem, this.connection))
        val result2 = execute(cpl, UpdateAndDeleteResultSubject(result1))
        retv.add(result2.EffectedRows.sum())
    }

    return retv.toIntArray()
}
