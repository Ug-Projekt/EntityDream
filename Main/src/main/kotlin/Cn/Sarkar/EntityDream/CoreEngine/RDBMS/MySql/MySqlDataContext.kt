/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.MySqlQueryTranslator
import java.sql.Connection

abstract class MySqlDataContext(override var connection: Connection) : IDataContext() {
    override fun queryTranslator(): IQueryTranslator = MySqlQueryTranslator()
}