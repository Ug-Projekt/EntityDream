/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/14/17
Time: 11:05 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Function.IDBFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.SuperBlock
interface FromWhat

data class FromColumn(var SourceName: String, var AliasName: String? = null) : FromWhat
data class FromFunction(var Function: IDBFunction, var AliasName: String? = null) : FromWhat
class Select(vararg select: FromColumn, var top: Int? = null, var distinct: Boolean = false) : SuperBlock
{
    val selectors = ArrayList<FromColumn>()
    init {
        selectors.addAll(select)
    }
}