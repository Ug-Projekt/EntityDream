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
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.CreateTable.CreateTableExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute


fun <TABLE: IDBTable, ENTITY : IDBEntity> IDataContext.dbCollection(table: TABLE, itemGenerator: (context: IDataContext) -> ENTITY) : QueriableCollection<ENTITY> =
        QueriableCollection(this, table, null, { itemGenerator(this@dbCollection) })


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
fun IDataContext.executeUpdateQuery(): IntArray {
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
fun IDataContext.executeInsertQuery(): Array<IDItem> {
    val retv = ArrayList<IDItem>()

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
        val result2 = execute(cpl, InsertResultSubject(result1, arrayOf()))
        retv.addAll(result2.Ids)
    }

    return retv.toTypedArray()
}

        //ۋەلىسپىتنىڭ ياكى موتوسىكىلىتنىڭ كەينىگە قىزلارنى مىندۈرۈپ ماڭغاندا موللاق ئەتتۈرۈش ۋەقەسى
        //قىزلارغا قاراپ پۇتلىشىپ كىتىش ۋەقەسى
//ئەڭ ئازاپلىق يىمىگەن مانتىنىڭ پۇلىنى تۆلەش ۋەقەسى
//پالاكەتلىك قىلىپ باشقىلارنىڭ نەرسىسىنى بۇزۇپ قويۇش ۋەقەسى
// ئەڭ ئازاپلىق ئاچقۇچ ئۇنتۇپ قىلىش ۋەقەسى

fun IDataContext.createNewTables(vararg tables: IDBTable) : Boolean
{
    val cpl = clonedPipeLine
    val result = execute(cpl, CreateTableSubjet(*tables.map { CreateTableExpression(it) }.toTypedArray()))
    return result.exception == null
}

/**
 * * ئوبىيكىت كىلونلاش
 * * 对象克隆
 * Clone Objects
 */
fun <T> T.cloneObjects(context: IDataContext) : T = context.execute(context.clonedPipeLine, ObjectCloneSubject(this as Any)).ClonedObject as? T ?:
        throw Exception("This object is default not supported, please add your ObjectCloner Interceptor, نۆۋەتتىكى تىپنى كىلونلاشنى قوللىمايدۇ، ئۆزىڭىزنىڭ مۇشۇ ئوبىيكىتنى كىلونلاپ بىرىدىغان قىستۇرمىسىنى قوشۇڭ، 不支持克隆此对象， 请添加您的克隆插件。")

