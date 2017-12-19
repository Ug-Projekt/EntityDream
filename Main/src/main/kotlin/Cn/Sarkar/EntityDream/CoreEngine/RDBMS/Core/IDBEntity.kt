/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 3:12 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

interface IDBEntity {
    var DataContext: IDataContext
    var Table: IDBTable
    var values: ValuesCacheItem?
    var FromDB: Boolean
    companion object {
        //val autoDetectID = "****Auto Detect****"
    }
}