/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:21 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SuperBlock

class Order private constructor(val Name: String)
{
    companion object {
        val Asc = Order("ASC")
        val Desc = Order("DESC")
    }
}

class OrderBy(
        var Name: String,
        var orderMode: Order
) : SuperBlock {

}