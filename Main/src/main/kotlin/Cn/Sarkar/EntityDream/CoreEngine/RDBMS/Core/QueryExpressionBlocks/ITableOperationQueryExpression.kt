/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/1/17
Time: 10:40 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

/**
 * جەدۋەلگە مەشغۇلات قىلىدىغان QueryExpression
 * بۇنى QueryExpression غا ئىشلىتىمىز
 * بۇ ئۇلىنىش ئىغىزىغا ۋارىسلىق قىلغان QueryExpression نىڭ جەدۋەلگە مەشغۇلات قىلىدىغان QueryExpression ئىكەنلىكىنى بىلدۈرىدۇ
 * يەنى بۇ نۆەتتىكى QueryExpression نىڭ زادى قايسى جەدۋەلگە مەشغۇلات قىلىدىغانلىقىنى ئۆزىنىڭ table  خاسلىقى ئارقىلىق ئايدىڭلاشتۇرىدۇ
 * مەسىلەن ئىشلىتىش ئورنى يىڭى بىر Entity نى ساندانغا كىرگۈزگەندە ئۇنىڭدىن قايتىدىغان ئاپتوماتىك ئاينىيدىغان ئىستوننىڭ قىممىتىنى ئىلىشتا قايسى ئىستوننىڭ ئاپتوماتىك ئاينىيدىغان تىپتىكى
 * ئىستون ئىكەنلىكىنى بىلىشكە توغرا كىلىدۇ، بىز ئاپتوماتىك ئاينىيدىغان ئىستوننى مۇشۇ ئۇلىنىش ئىغىزىنىڭ table خاسلىقىدىن ئالالايمىز.
 */
interface ITableOperationQueryExpression {
    var table: IDBTable
}