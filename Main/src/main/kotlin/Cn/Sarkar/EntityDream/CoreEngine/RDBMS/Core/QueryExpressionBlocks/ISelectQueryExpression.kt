/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/29/17
Time: 6:24 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*

/**
 * ئىزدەش تۈرىگە تەۋە ئىپادە
 */
interface ISelectQueryExpression : IQueryExpression, ITableOperationQueryExpression
{
    var Select: Select
    var From: From
    var Where: Where?
    var GroupBy: GroupBy?
    var Having: Having?
    var OrderBy: OrderBy?
}