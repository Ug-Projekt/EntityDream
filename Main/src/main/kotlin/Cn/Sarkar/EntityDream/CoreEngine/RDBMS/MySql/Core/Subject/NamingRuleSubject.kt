/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/18/18
Time: 11:23 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Subject

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class NamingRuleSubject : IPipeLineSubject {
    override val Name: String = "Naming Rule for Generate Index, PrimaryKey, Unique Constraints"
    override val Description: String = "Naming Rule for Generate Index, PrimaryKey, Unique Constraints"
    var PrimaryKeyNamingRules: (IDBTable.(Array<IDBColumn<*>>) -> String)? = null
    var UniqueNamingRules: (IDBTable.(Array<IDBColumn<*>>) -> String)? = null
    var IndexNamingRules: (IDBTable.(Array<IDBColumn<*>>) -> String)? = null
}