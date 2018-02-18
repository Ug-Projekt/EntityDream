/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:37 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.KeyValuePair
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SuperBlock

class Set(var Values: ArrayList<KeyValuePair<IDBColumn<*>/*Field Name*/, SqlParameter/*Field Parameter*/>> = ArrayList()) : SuperBlock {
}