package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.ParameterContent
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryGroup
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryGroupSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.IEntityBindQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object QueryGrouper : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDreams.Core.QueryGroup")
    override val info: FeatureInfo = FeatureInfo(
            "Query Group",
            "بۇ بولسا SQL جۈملىسى گورۇپپىلىغۇچ، ئوخشاش قۇرۇلمىدىكى SQL جۈملىلىرىنى گورۇپپىلاپ چىقىدۇ.",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    )

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is QueryGroupSubject){
            if (subject.translateResult == null) throw Exception("*************Translate result cannot be null, ***************")
            val TrResult = subject.translateResult!!

            var existingGroup = subject.groups[TrResult.md5Key]
            if (existingGroup == null)
            {
                existingGroup = QueryGroup(TrResult)
                subject.groups.put(existingGroup.query.md5Key, existingGroup)
            }

            val uniqueKey = if (TrResult.expression is IEntityBindQueryExpression) // ئەگەر نۆۋەتتىكى مەشغۇلات قىستۇرۇش مەشغۇلاتى بولسا Entity ۋە ئومومى نەتىجىسىنى باغلاپ تۇرىدىغان uniqueKey نىڭ قىممىتىنى كەينىگە ئۆتكۈزۈپ بىرىمىز
                TrResult.expression.entityUniqueKey
            else //ئەگەر نۆۋەتتىكى مەشغۇلات قىستۇرۇش مەشغۇلاتى بولمىسا بۇ يەردىكى نەتىجە بىلەن Entity نى باغلايدىغان ئاچقۇچلۇق قىممەت uniqueKey غا بىزنىڭ ھاجىتىمىز چۈشمەيدۇ
                ""

            existingGroup.content.add(ParameterContent(uniqueKey, TrResult.parameters.toTypedArray()))
        }
    }
}
