/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 4:27 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import java.io.Serializable

interface SqlParameter : Serializable{
    var Value: Any
}
data class NormalParameter(override var Value: Any) : SqlParameter
data class MappedParameter(override var Value: Any, var DataType: IDBDataType<*>) : SqlParameter
