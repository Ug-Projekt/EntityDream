/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:05 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SuperBlock
import java.io.Serializable

interface FromWhat : Serializable{
    var DefaultValue: Any
}

data class FromColumn(var SourceColumnName: String, var AliasName: String? = null, override var DefaultValue: Any) : FromWhat
data class FromFunction(var Function: IDBFunction, var AliasName: String, override var DefaultValue: Any) : FromWhat
class Select(vararg select: FromWhat, var top: Int? = null, var offset: Int? = null, var distinct: Boolean = false) : SuperBlock
{
    val selectors = ArrayList<FromWhat>()
    init {
        selectors.addAll(select)
    }
}


val FromColumn.validName: String get() = if (this.AliasName != null) this.AliasName!! else this.SourceColumnName