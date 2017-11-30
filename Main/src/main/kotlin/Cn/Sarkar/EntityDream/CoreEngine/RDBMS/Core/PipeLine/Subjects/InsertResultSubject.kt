/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/30/17
Time: 4:44 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
data class IDItem(var uniqueMd5Key: String, var id: Int)

data class InsertResultSubject(var result: QueryExecutionSubject, var Ids: Array<IDItem>) : IPipeLineSubject {
    override val operationName: String = "Insert Result Subject"
    override val operationDescription: String = "يىڭى قوشۇلغان ئۇچۇرلارنىڭ نەتىجىسىنى ئوقۇش"
}