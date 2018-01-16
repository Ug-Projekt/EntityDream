/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/30/17
Time: 4:44 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

/**
 * @param id يىڭى ھاسىل قىلىنغان تەرتىپ نومۇرى
 * @param uniqueMd5Key
 * پەرىقلەندۈرۈش ئاچقۇچ قىممەت،
 * ئاساسلىقى يىڭى قوشۇلغان Entity نىڭ Auto Increment خاسلىق
 */
data class IDItem(var uniqueMd5Key: String, var id: Long)

data class InsertResultSubject(var result: QueryExecutionSubject, var Ids: Array<IDItem>) : IPipeLineSubject {
    override val Name: String = "Insert Result Subject"
    override val Description: String = "يىڭى قوشۇلغان ئۇچۇرلارنىڭ نەتىجىسىنى ئوقۇش"
}