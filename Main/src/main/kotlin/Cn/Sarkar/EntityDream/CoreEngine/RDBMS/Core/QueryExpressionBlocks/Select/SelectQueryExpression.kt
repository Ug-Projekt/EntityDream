/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:19 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

open class SelectQueryExpression(
        override var table: IDBTable,
        override var Select: Select,
        override var From: From,
        override var Where: Where? = null,
        override var GroupBy: GroupBy? = null,
        override var Having: Having? = null,
        override var OrderBy: OrderBy? = null
) : ISelectQueryExpression {
}