/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/23/17
Time: 9:03 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.executeSelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.SelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.count
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.slice
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.deserializeFromByteArray
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.serializeToByteArray
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

fun <TABLE : IDBTable, ENTITY : IDBEntity> QueriableCollection<ENTITY>.query(queryAction: QueriableCollection<ENTITY>.(table: TABLE) -> Unit) = this.apply { queryAction(table as TABLE) }

class QueriableCollection<ENTITY : IDBEntity>(override var Context: IDataContext, override var table: IDBTable, where: WhereItemCondition? = null, override var ItemGenerator: () -> ENTITY) : ArrayList<ENTITY>(), ISelectQueryExpression by table.SelectQuery, IQueriableCollection<ENTITY> {

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
            if (this.Where == null) this.Where = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where(where)
            else this.Where!!.condition = And(this.Where!!.condition, where)
        }

        cloneObjects()

    }

    fun loadFromLocal() {
        if (cached) return
        super.clear()
        val result = Context.executeSelectQuery(this)
        result.forEach {
            super.add(ItemGenerator().apply {
                values = it
            })
        }
        cached = true
    }

    override fun iterator(): MutableIterator<ENTITY> {
        loadFromLocal()
        return super.iterator()
    }

    override val size: Int get() {

        val key = table.PrimaryKey.first()
        val size = this count key
        return if (cached) super.size + size else size
    }
}