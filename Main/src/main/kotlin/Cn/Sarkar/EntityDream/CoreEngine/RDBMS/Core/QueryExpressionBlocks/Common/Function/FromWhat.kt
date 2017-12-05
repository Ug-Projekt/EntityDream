/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/5/17
Time: 1:26 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function

interface FromWhat

data class FromColumn(var Value: () -> String) : FromWhat
data class FromFunction(var Value: () -> IDBFunction) : FromWhat


typealias FuncFromWhat = FromWhat
typealias FuncFromColumn = FromColumn
typealias FuncFromFunction = FromFunction