/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/3/17
Time: 7:23 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ValuesCacheItem
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.getDefaultValue

fun <T : IDBTable> T.insert(insertAction: ValuesCacheItem.(table: T) -> Unit) : ValuesCacheItem{
    val values = ValuesCacheItem()
    this.Columns.forEach {
        values.put(it.ColumnName, it.getDefaultValue())
    }
    values.insertAction(this)
    return values
}