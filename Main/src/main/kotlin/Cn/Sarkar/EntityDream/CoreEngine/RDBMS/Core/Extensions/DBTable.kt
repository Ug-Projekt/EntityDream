/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 12:24 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

abstract class DBTable(override var TableName: String) : IDBTable {
//    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(*primaryKeys())
    override var Columns: Array<IDBColumn<*>> = arrayOf()
}