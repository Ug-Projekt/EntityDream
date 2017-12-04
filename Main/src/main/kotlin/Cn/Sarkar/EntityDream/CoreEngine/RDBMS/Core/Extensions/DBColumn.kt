/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.AutoIncrementProperty
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.VarChar
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ForeignKey
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable

typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int

class DBIdColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(Unsigned = true),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(true),
        override var Unique: Boolean = true,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = true
) : IDBColumn<Int>

class DBStringColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<String> = VarChar(100),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = false) : IDBColumn<String>

class DBIntColumn(
        override var Table: IDBTable,
        override var ColumnName: String,
        override var NotNull: Boolean = true,
        override var DataType: IDataType<Int> = DBInt(),
        override var AutoIncrement: AutoIncrementProperty = AutoIncrementProperty(false),
        override var Unique: Boolean = false,
        override var ForeignKey: ForeignKey? = null,
        override var Index: Boolean = false
) : IDBColumn<Int>