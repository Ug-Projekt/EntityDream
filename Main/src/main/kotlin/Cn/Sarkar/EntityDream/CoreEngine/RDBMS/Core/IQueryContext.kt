/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 4:36 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression

interface IQueryContext {
    var updateTasks: HashMap<String, UpdateQueryExpression>
    var deleteTasks: HashMap<String, DeleteQueryExpression>
    var insertTasks: HashMap<String, InsertQueryExpression>
}