/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 10:54 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.IDBCollection

open class DBCollection<ITEM : IDBEntity>(var context: IDataContext, val table: IDBTable, var itemGenerator: () -> ITEM) : ArrayList<ITEM>(), IDBCollection {
//    override var Fields: MutableList<IDBField<Any>> = ArrayList()
//
//    private var reCache = true
//
//    private fun load(): ArrayList<ITEM>{
//        if (reCache)
////        {
////            super.clear()
////            var item = itemGenerator()
////
////            val expression = SelectQueryExpression(Select(FromColumn(primaryKey)), From(FromTable(item.TableName)), Where(whereItemCondition))
////            val query = context.queryTranslator().Translate(expression)
////
////            val statement = context.connection.prepareStatement(query.sqlQuery)
////            query.parameters.forEachIndexed { index, any -> statement.WriteParameters(index + 1, any) }
////            val result = statement.executeQuery()
////
////            while (result.next())
////            {
////                item = itemGenerator()
//////                item.Container = this
////                item.ID = readResult(result, primaryKey, item.ID) as Int
////                super.add(item)
////            }
//
//            reCache = false
//        }
//        return this
//    }
//
//    override val size: Int get(){ load(); return super.size}
//
//    override fun iterator(): MutableIterator<ITEM> { load();return super.iterator() }
//
//    private fun ITEM.addToInsertTask(){
//        val fields = this.Fields.filter { !it.AutoIncrement }
//        val addTask = InsertQueryExpression(InsertInto(this.TableName, fields.map { it.FieldName }.toTypedArray()), Values(fields.map { it.Cache }.toTypedArray()))
//
//        while (context.insertTasks[this.key] != null){
//            this.ID-- //ئەگەر ئىككى قىتىم ئەزا قوشقان بولسا ئىككىنچى قىتىملىق قوشۇلغان ئەزا ئاۋالقىسىنى باستۇرۋەتمەسلىك ئۈچۈن تەرتىپ نومۇرىنى ئۆزگەرتىۋىتىمىز.
//        }
//
//        context.insertTasks.put(this.key, Pair(addTask, this))
//        context.updateTasks.remove(this.key)
//    }
//
//    override fun add(element: ITEM): Boolean {
//        element.addToInsertTask()
//        return super.add(element)
//    }
//
//    private fun ITEM.addToRemoveTask(){
//        context.deleteTasks.put(this.key, DeleteQueryExpression(DeleteFrom(this.TableName), Where(Equal({ IDBEntity.ID}, {this.ID}))))
//    }
//
//    override fun remove(element: ITEM): Boolean {
//        element.addToRemoveTask()
//        return super.remove(element)
//    }
//
//    override fun clear() {
//        load()
//        super.forEach { it.addToRemoveTask() }
//        reCache = true
//        super.clear()
//    }
//
//    override fun addAll(elements: Collection<ITEM>): Boolean {
//        elements.forEach { it.addToInsertTask() }
//        return super.addAll(elements)
//    }
//
//    override fun removeAll(elements: Collection<ITEM>): Boolean {
//        elements.forEach { it.addToRemoveTask() }
//        return super.removeAll(elements)
//    }
//
//    override fun get(index: Int): ITEM { load();return super.get(index) }
//
//    override fun isEmpty(): Boolean { load();return super.isEmpty() }
}