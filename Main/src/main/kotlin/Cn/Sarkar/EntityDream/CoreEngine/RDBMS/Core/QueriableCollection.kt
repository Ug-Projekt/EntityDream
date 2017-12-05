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
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

fun <TABLE: IDBTable, ENTITY: IDBEntity> QueriableCollection<ENTITY>.query(queryAction: QueriableCollection<ENTITY>.(table: TABLE) -> Unit) = this.apply{ queryAction(table as TABLE) }

class QueriableCollection<ENTITY: IDBEntity>(override var Context: IDataContext, override var table: IDBTable, where: WhereItemCondition? = null, override var ItemGenerator: () -> ENTITY) : ArrayList<ENTITY>(), ISelectQueryExpression by table.SelectQuery, IQueriableCollection<ENTITY> {
    override var cached: Boolean = false
    override var Level: Int = 0
    init {
        /**
         * ئەگەر نۆۋەتتىكى ئوبىيكىت بىرگە كۆپ ماسلىق مۇناسىۋەت تۈپەيلى كىلىپ چىققان ئوبىيكىت بولسا
         * يەنە قايتىدىن بارلىق ئوبىيكىتلار ئىچىدىن ئىزدەشتىن ساقلىنىش ئۈچۈن where غا بىر شەرىت بىرىلىدۇ
         * مەسىلەن
         * select xxxx from xxxx where xxxx AND {OneToManyObj}.{PrimaryKeyColumn} = {xxxx}
         */
        if (where != null)
        {
            if (this.Where == null) this.Where = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where(where)
            else this.Where!!.condition = And(this.Where!!.condition, where)
        }
    }

    fun loadFromLocal(){
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
}