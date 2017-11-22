/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:19 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Where

class SelectQueryExpression(
        var Select: Select,
        var From: From,
        var Where: Where?,
        var GroupBy: GroupBy?,
        var Having: Having?,
        var OrderBy: OrderBy? = null
) : IQueryExpression