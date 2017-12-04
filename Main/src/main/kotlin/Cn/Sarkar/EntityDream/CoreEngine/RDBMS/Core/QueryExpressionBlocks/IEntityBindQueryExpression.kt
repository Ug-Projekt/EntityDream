/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 12:53 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks

/**
 * بۇ Entity بىلەن باغلىنىدىغان Expression ئىكەنلىكىنى بىلدۈرىدۇ
 */
interface IEntityBindQueryExpression {
    /**
     *يىڭى Entity قىستۇرۇلغاندا قىستۇرۇلغان Entity بىلەن باغلىنىپ تۇرىدىغان ئاچقۇچلۇق قىممەت
     */
    val entityUniqueKey: String
}