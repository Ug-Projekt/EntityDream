/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/13/18
Time: 10:12 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TypeAdapterSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute


typealias DBInt = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int
typealias DBFloat = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Float
typealias DBDouble = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Double
typealias DBChar = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Char
typealias DBDateTime = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.DateTime
typealias DBDate = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Date
typealias DBTime = Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Time


fun IDBDataType<*>.toLocalDBDmlString(dataContext: IDataContext) : String {
    val cpl = dataContext.clonedPipeLine

    val result = dataContext.execute(cpl, TypeAdapterSubject(this))
    return result.OutPutString ?: throw Exception("Cannot find type adapter, تىپ ماسلاشتۇرغۇچنى تاپالمىدى, 找不到对应的类型Adapter（匹配器）")
}