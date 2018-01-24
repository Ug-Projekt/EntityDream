/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 2:12 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

class Unique(var name: String, vararg columns: IDBColumn<*>)
{
    var columns = arrayOf(*columns)
}

class Index(var name: String, vararg columns: IDBColumn<*>)
{
    var columns = arrayOf(*columns)
    var isUnique: Boolean = false
    var size: Int = -1
}

class PrimaryKey(var name: String = "PrimaryKey", vararg columns: IDBColumn<*>)
{
    var columns = arrayOf(*columns)
}

interface IDBTable {
    var TableName: String //جەدۋەل ئىسمى
    var PrimaryKey: PrimaryKey
    var Uniques: Array<Unique>
    var Indexes: Array<Index>
    var Columns: Array<IDBColumn<*>>
    var Comment: String
}