/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 9:16 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBField
class ValuesCacheItem(val uniqueMd5Key: String) : LinkedHashMap<IDBColumn<*>, Any>()

class ValuesCache : LinkedHashMap<String, ValuesCacheItem>(){

}