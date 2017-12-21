/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/5/17
Time: 9:46 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

interface IQueriableCollection<ENTITY : IDBEntity> {
    var Context: IDataContext
    var ItemGenerator: () -> ENTITY
    fun ValuesCacheItem.toEntity() : ENTITY
    /**
     * دەرىجە قاتلىمى،
     * يەنى مەسىلەن where {} orderBy xxxx take xxxx شەكلىدە نۇرغۇن شەرىت، بۆلەكلەرنى بىر بىرىگە ئۇلاپ يازغان ۋاقتىمىزدا
     * ھاسىل بولغان SQL ئىپادىلىرىنىڭ نۆۋەتتىكى DataContext دىكى QueriableCollection نى بۇزغۇنچىلىققا ئۇچراتماسلىقى ئۈچۈن بىز QueriableCollection نى يىڭىدىن ئىنىقلاشقا توغرا كىلىدۇ
     * لىكىن نۇرغۇن ئىپادىلەرنى بىر بىرىنىڭ كەينىگە ئۇلاپ يىزىش ھەر بىر ئىپادە ئۈچۈن بىر دانە QueriableCollection ھاسىل قىلىپ ئۈنۈمسىز ئوبىيكىتلارنى ھاسىل قىلىشتىن ساقلىنىش ئۈچۈن
     * نۆۋەتتىكى QueriableCollection نىڭ دەرىجە قاتلىمى ئارقىلىق ھۆكۈم قىلىپ ئارتۇق ئوبىيكىت ھاسىل قىلىشتىن ساقلىنىمىز،
     * ئەگەر غول دەرىجە يەنى بىرىنچى دەرىجە بولسا قىممىتى 0، ئىككىنچى دەرىجە بولسا قىممىتى 1، ئۈچىنچى بولسا 2.... مۇشۇنداق ھالەتتە داۋاملىشىدۇ
     */
    var Level: Int
    /**
     * غەملەككە غەملەپ بولدىمۇ؟
     */
    var cached: Boolean

    val clonedSelect: Select
    val clonedFrom: From
    val clonedWhere: Where?
    val clonedGroupBy: GroupBy?
    val clonedHaving: Having?
    val clonedOrderby: OrderBy?
    val size: Int
}