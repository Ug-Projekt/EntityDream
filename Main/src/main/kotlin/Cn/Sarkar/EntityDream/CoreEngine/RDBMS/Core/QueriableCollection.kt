/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/23/17
Time: 9:03 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select.SelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

class QueriableCollection<ENTITY>(var context: IDataContext, val table: IDBTable, var expression: SelectQueryExpression) : ArrayList<ENTITY>() {

}