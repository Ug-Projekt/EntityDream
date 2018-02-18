/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/24/17
Time: 2:35 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.SqLiteQueryTranslator
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

class QueryTranslator(var context: PipeLineContext<IPipeLineSubject, IDataContext>) {
    val translator = SqLiteQueryTranslator(context.featureContext)
    fun process(subject: TranslationSubject) {
        subject.translationResult = translator.Translate(subject.expression)
    }

    companion object : PipeLineFeature<IPipeLineSubject, IDataContext>() {
        override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDreams.MySql.QueryTranslationInterpolatory")
        override val info: FeatureInfo = FeatureInfo(
                "Query Trnalsator",
                "ئورتاق بولغان SQL تەسۋىرلەش ئوبىيكىتىنى MySql جۈملىسىگە تەرجىمە قىلىدۇ",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )

        override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
            if (subject is TranslationSubject) {
                QueryTranslator(this).process(subject)
            }
        }

    }
}