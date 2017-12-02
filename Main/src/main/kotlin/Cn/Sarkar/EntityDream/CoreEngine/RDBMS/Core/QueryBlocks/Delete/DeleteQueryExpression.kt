/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:32 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Delete

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IForTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IUpdateQueryExpression

class DeleteQueryExpression(
        override var table: IDBTable,
        var deleteFrom: DeleteFrom,
        var where: Where
) : IUpdateQueryExpression, IForTable {
}