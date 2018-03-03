/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:38 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ITableOperationQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.IUpdateQueryExpression

open class UpdateQueryExpression(
        val entity: IDBEntity,
        override var table: IDBTable,
        var update: Update,
        var set: Set,
        var where: Where
) : IUpdateQueryExpression, ITableOperationQueryExpression {
}