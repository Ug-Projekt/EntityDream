/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:29 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Insert

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IEntityBinder
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IForTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IUpdateQueryExpression

class InsertQueryExpression(
        override val entityUniqueKey: String,
        override var table: IDBTable,
        var insertInto: InsertInto,
        var values: Values
) : IUpdateQueryExpression, IForTable, IEntityBinder {

}