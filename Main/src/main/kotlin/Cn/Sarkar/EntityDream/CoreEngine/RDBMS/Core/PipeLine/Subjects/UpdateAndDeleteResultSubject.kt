/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/29/17
Time: 4:51 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class UpdateAndDeleteResultSubject(var executionSubject: QueryExecutionSubject) : IPipeLineSubject {
    override val Name: String = "UpdateAndDeleteResult"
    override val Description: String = "يىڭىلاش ۋە ئۆچۈرۈش نەتىجىسىنى ئوقۇش"
    var EffectedRows: IntArray = IntArray(0, { 0 })
}