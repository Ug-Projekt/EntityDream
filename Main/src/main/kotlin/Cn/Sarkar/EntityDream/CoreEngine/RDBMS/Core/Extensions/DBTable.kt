/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 12:24 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*

abstract class DBTable(override var TableName: String = AutoDetect) : IDBTable {
//    override var PrimaryKey: Array<IDBColumn<*>> = arrayOf(*primaryKeys())
    override var Columns: Array<IDBColumn<*>> = arrayOf()
    override var Comment: String = ""
    override var PrimaryKey: PrimaryKey = PrimaryKey()
    override var Uniques: Array<Unique> = arrayOf()
    override var Indexes: Array<Index> = arrayOf()

    companion object {
        val AutoDetect = "****Auto Detect****"
    }

    init {
        if (TableName == AutoDetect) TableName = this::class.java.simpleName
    }
}





fun IDBTable.primaryKey(name: String, vararg columns: IDBColumn<*>) = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PrimaryKey(name, *columns).apply { PrimaryKey = this }
fun IDBTable.index(name: String, vararg columns: IDBColumn<*>) = Index(name, *columns).apply { Indexes = arrayOf(*Indexes, this) }
fun IDBTable.unique(name: String, vararg columns: IDBColumn<*>) = Unique(name, *columns).apply { Uniques = arrayOf(*Uniques, this) }

fun IDBTable.primaryKey(vararg columns: IDBColumn<*>) = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PrimaryKey("", *columns).apply { PrimaryKey = this }
fun IDBTable.index(vararg columns: IDBColumn<*>) = Index("", *columns).apply { Indexes = arrayOf(*Indexes, this) }
fun IDBTable.unique(vararg columns: IDBColumn<*>) = Unique("", *columns).apply { Uniques = arrayOf(*Uniques, this) }

infix fun Index.unique(unique: Boolean) = this.apply { isUnique =  unique}
infix fun Index.length(length: Int) = this.apply { size = length }