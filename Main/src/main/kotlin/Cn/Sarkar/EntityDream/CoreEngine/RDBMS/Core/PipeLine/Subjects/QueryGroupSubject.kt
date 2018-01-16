/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 4:53 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult

/**
 * @param parameters
 * پارامىتىرلار
 * @param uniqueIdentificationKey
 * بۇ بولسا تەرجىمە قىلىنىپ بولغان SQL جۈملىسىنىڭ MD5 قىممىتى بولماستىن بەلكى مۇشۇ ھەر بىر گورۇپپا ئەزاسىنى بىر بىرىدىن پەرىقلەندۈرىدىغان كود،
 * بۇنى ئاساسەن ساندانغا ئۇچۇر كىرگۈزگەندە ئاپتوماتىك ئاينىيدىغان قىممەتنى سانداندىن قايتۇرۇپ ئىلىپ ساندانغا قىستۇرۇلغان ئوبىيكىتنىڭ ماس خاسلىقلىرىغا قىممەت بەرگەندە بىز مۇشۇ خاسلىققا
 * ئاساسەن ئاۋال ساندانغا قىستۇرۇلغان ئوبىيكىتنى ئىزدەپ تاپىمىز، قىسقىسى بۇ خاسلىق نۆۋەتتىكى مەشغۇلات قىلىنغان ئوبىيكىتكە ئىرىشىشتىكى ھالقىلىق قىممەت
 */
data class ParameterContent(var uniqueIdentificationKey: String, var parameters: Array<SqlParameter>)
data class QueryGroup(
        var query: TranslateResult,
        var content: MutableList<ParameterContent> = ArrayList()
)

/**
 * @param groups
 * ئاچقۇچى تەرجىمە قىلىنىپ بولغان SQL جۈملىسىنىڭ MD5 قىممىتى
 * @param translateResult
 *تەرجىمە نەتىجىسى
 */
data class QueryGroupSubject(val groups: HashMap<String, QueryGroup> = LinkedHashMap(), var translateResult: TranslateResult? = null) : IPipeLineSubject {
    override val Name: String = "Group SQL Query"
    override val Description: String = "ئوخشاش قۇرۇلمىدىكى SQL جۈملىرىنى گورۇپپىلايدۇ"
}

