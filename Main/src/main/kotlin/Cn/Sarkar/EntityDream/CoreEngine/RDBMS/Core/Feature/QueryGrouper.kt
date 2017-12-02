package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.ParameterContent
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryGroup
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryGroupSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IEntityBinder
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object QueryGrouper : PipeLineFeature<IPipeLineSubject, IDataContext, Any?>() {
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
            var existingGroup = subject.groups[subject.translateResult.md5Key]
            if (existingGroup == null)
            {
                existingGroup = QueryGroup(subject.translateResult)
                subject.groups.put(existingGroup.query.md5Key, existingGroup)
            }

            val uniqueKey = if (subject.translateResult.expression is IEntityBinder)
                subject.translateResult.expression.entityUniqueKey
            else
                ""

            existingGroup.content.add(ParameterContent(uniqueKey, subject.translateResult.parameters.toTypedArray()))
        }
    }
}
