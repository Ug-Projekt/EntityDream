package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.QueryExecutionSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object QueryExecuter : PipeLineFeature<IPipeLineSubject, IDataContext, Unit>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDreams.Core.QueryExecute")
    override val info: FeatureInfo = FeatureInfo(
            "Query Execute",
            "بۇ بۆلەك SQL جۈملىسىنى يۈرگۈزىدۇ.",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    )

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is QueryExecutionSubject)
        {
            subject.
        }
    }
}