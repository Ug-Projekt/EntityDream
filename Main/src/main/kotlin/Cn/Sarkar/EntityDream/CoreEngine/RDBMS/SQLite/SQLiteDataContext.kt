/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature.DataTypeAdapter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature.QueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature.TableCreator
import Cn.Sarkar.EntityDream.Pipeline.Extension.installFeature
import java.sql.Connection

abstract class SQLiteDataContext(override var connection: Connection) : IDataContext() {
    init {
        pipeLine.installFeature(QueryTranslator)
        pipeLine.installFeature(TableCreator)
        pipeLine.installFeature(DataTypeAdapter)
    }
}