/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/23/17
Time: 9:03 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeSelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.simpleWhereCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GenerateDeleteTaskSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GenerateInsertTaskSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.clone
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.deserializeFromByteArray
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.serializeToByteArray
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute

fun <TABLE : IDBTable, ENTITY : IDBEntity> QueriableCollection<ENTITY>.query(queryAction: QueriableCollection<ENTITY>.(table: TABLE) -> Unit) = this.apply { queryAction(table as TABLE) }

class QueriableCollection<ENTITY : IDBEntity>(override var Context: IDataContext, override var table: IDBTable, private var where: WhereItemCondition? = null, override var ItemGenerator: () -> ENTITY) : ArrayList<ENTITY>(), ISelectQueryExpression by table.SelectQuery, IQueriableCollection<ENTITY> {

    private var cachedSelectBytes: ByteArray = byteArrayOf()
    private var cachedFromBytes: ByteArray = byteArrayOf()
    private var cachedWhereBytes: ByteArray? = null
    private var cachedGroupByBytes: ByteArray? = null
    private var cachedHavingBytes: ByteArray? = null
    private var cachedOrderbyBytes: ByteArray? = null

    override val clonedSelect: Select get() = cachedSelectBytes.deserializeFromByteArray()
    override val clonedFrom: From get() = cachedFromBytes.deserializeFromByteArray()
    override val clonedWhere: Where? get() = cachedWhereBytes?.deserializeFromByteArray()
    override val clonedGroupBy: GroupBy? get() = cachedGroupByBytes?.deserializeFromByteArray()
    override val clonedHaving: Having? get() = cachedHavingBytes?.deserializeFromByteArray()
    override val clonedOrderby: OrderBy? get() = cachedOrderbyBytes?.deserializeFromByteArray()
    override var cached: Boolean = false
    override var Level: Int = 0

    override fun ValuesCacheItem.toEntity() : ENTITY = ItemGenerator().apply { values = this@toEntity; FromDB = true }

    /**
     * پەقەت نۆۋەتتىكى QueriableCollection قۇرۇلۇپ بولغاندىن كىيىن كىلونلايدىغان ئوبىيكىتلىرىنى ئۆزگەرتىشنىڭ زۆرۈرىيىتى بولمىغانلىقى ۋە
     * ئۈنۈمنى ئاشۇرۇش ئۈچۈن ھەر قىتىم كىلونلايدىغان مۇھىم ئوبىيكىتلارنى غەملەككە كىلونلاپ ساقلىۋالدۇق
     */
    private fun cloneObjects() {

        cachedFromBytes = From.serializeToByteArray()
        cachedSelectBytes = Select.serializeToByteArray()
        cachedWhereBytes = Where?.serializeToByteArray()
        cachedGroupByBytes = GroupBy?.serializeToByteArray()
        cachedHavingBytes = Having?.serializeToByteArray()
        cachedOrderbyBytes = OrderBy?.serializeToByteArray()
    }


    init {
        /**
         * ئەگەر نۆۋەتتىكى ئوبىيكىت بىرگە كۆپ ماسلىق مۇناسىۋەت تۈپەيلى كىلىپ چىققان ئوبىيكىت بولسا
         * يەنە قايتىدىن بارلىق ئوبىيكىتلار ئىچىدىن ئىزدەشتىن ساقلىنىش ئۈچۈن where غا بىر شەرىت بىرىلىدۇ
         * مەسىلەن
         * select xxxx from xxxx where xxxx AND {OneToManyObj}.{PrimaryKeyColumn} = {xxxx}
         */
        if (where != null) {
            if (this.Where == null) this.Where = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where(where!!)
            else this.Where!!.condition = And(this.Where!!.condition, where!!)
        }

        if (Where != null)
        {
            val value = ((Where!!.condition as Equal))
            println(value)
            val cv = value.clone()
        }

        cloneObjects()
    }

    fun loadFromLocal() {
        if (cached) return
        super.clear()
        val result = Context.executeSelectQuery(this)
        result.forEach { super.add(it.toEntity()) }
        cached = true
    }

    override fun iterator(): MutableIterator<ENTITY> {
        loadFromLocal()
        return super.iterator()
    }

    override val size: Int
        get() {

            val key = table.PrimaryKey.first()
            val size = this count key
            return if (cached) super.size + size else size
        }


    override fun add(element: ENTITY): Boolean {
        val cpl = Context.clonedPipeLine
        val result = Context.execute(cpl, GenerateInsertTaskSubject(Context, table, element))
        return super.add(element)
    }

    override fun remove(element: ENTITY): Boolean {
        var whereCondition = where { element.simpleWhereCondition }
        if (where != null) whereCondition.andWhere { where!! }

        val subject = GenerateDeleteTaskSubject(element, whereCondition.Where!!)
        val result = Context.execute(Context.clonedPipeLine, subject)

        return super.remove(element)
    }

    fun first() : ENTITY = Context.executeSelectQuery(this take 1)[0].toEntity()
    fun last() : ENTITY = Context.executeSelectQuery(this take 1 orderByDesc table.Columns.first { it.AutoIncrement.autoIncrement })[0].toEntity()

    fun single() = if (size == 1) first() else throw Exception("contains more items, بىردىن ئارتۇق ئەزا بار، 已存在多个元素")
}

