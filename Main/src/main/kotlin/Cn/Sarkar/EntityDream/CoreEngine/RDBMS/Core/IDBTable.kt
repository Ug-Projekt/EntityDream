/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 2:12 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn

interface IDBTable {
    var TableName: String //جەدۋەل ئىسمى
    var PrimaryKey: Array<IDBColumn<*>>
    var Columns: Array<IDBColumn<*>>
}