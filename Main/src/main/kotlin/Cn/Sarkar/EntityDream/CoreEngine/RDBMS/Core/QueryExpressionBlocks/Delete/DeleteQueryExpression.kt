/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:32 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Delete

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ITableOperationQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.IUpdateQueryExpression

open class DeleteQueryExpression(
        val entity: IDBEntity,
        override var table: IDBTable,
        var deleteFrom: DeleteFrom,
        var where: Where
) : IUpdateQueryExpression, ITableOperationQueryExpression {
}