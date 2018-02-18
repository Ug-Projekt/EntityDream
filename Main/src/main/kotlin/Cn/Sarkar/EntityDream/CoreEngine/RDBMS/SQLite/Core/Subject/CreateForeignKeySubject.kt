/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/25/18
Time: 1:14 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Subject

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class CreateForeignKeySubject(var tables: Array<IDBTable>) : IPipeLineSubject {
    override val Name: String = "Create ForeignKey"
    override val Description: String = "Create ForeignKey"
    var exception: Exception? = null
}