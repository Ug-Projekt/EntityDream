/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 2:14 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

class DBSimpleColumn<KOTLINDATATYPE>(override var Table: IDBTable, override var ColumnName: String, override var NotNull: Boolean, override var DataType: IDataType<KOTLINDATATYPE>, override var AutoIncrement: Boolean, override var Unique: Boolean, override var Index: Boolean, override var ForeignKey: IDBColumn<*>? = null) : IDBColumn<KOTLINDATATYPE>
