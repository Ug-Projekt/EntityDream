/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 12:27 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Insert

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.SuperBlock

class InsertInto(
        var TableName: String,
        var Columns: Array<String>
) : SuperBlock {

}