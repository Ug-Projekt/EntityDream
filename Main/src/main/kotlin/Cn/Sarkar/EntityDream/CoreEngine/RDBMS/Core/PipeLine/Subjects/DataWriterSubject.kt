/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 5:51 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import java.sql.PreparedStatement

class DataWriterSubject(val Statement: PreparedStatement, val Index: Int, val Parameter: SqlParameter) : IPipeLineSubject {
    override val Name: String = "DataWriterSubject"
    override val Description: String = "PretapedStatement Parameter Writer Subject"
}