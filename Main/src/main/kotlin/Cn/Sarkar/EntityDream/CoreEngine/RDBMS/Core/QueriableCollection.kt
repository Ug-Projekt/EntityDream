/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/23/17
Time: 9:03 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQuery
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

class QueriableCollection<ENTITY: IDBEntity>(var context: IDataContext, override var table: IDBTable, where: WhereItemCondition? = null, itemFactoriy: () -> ENTITY) : ArrayList<ENTITY>(), ISelectQueryExpression by table.SelectQuery {
    init {
        /**
         * ئەگەر نۆۋەتتىكى ئوبىيكىت بىرگە كۆپ ماسلىق مۇناسىۋەت تۈپەيلى كىلىپ چىققان ئوبىيكىت بولسا
         * يەنە قايتىدىن بارلىق ئوبىيكىتلار ئىچىدىن ئىزدەشتىن ساقلىنىش ئۈچۈن where غا بىر شەرىت بىرىلىدۇ
         * مەسىلەن
         * select xxxx from xxxx where xxxx AND {OneToManyObj}.{PrimaryKeyColumn} = {xxxx}
         */
        if (where != null)
        {
            this.Where!!.condition = And(this.Where!!.condition, where)
        }
    }


}
