package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryExecutionSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.IUpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.WriteParameters
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
        if (subject is QueryExecutionSubject) {

            try {
                subject.statement = subject.connection.prepareStatement(subject.group.query.sqlQuery)
                subject.group.content.forEach {
                    it.parameters.forEachIndexed { index, any -> subject.statement!!.WriteParameters(index + 1, any) }
                    subject.statement!!.addBatch()
                }
                if (subject.group.query.expression is ISelectQueryExpression) subject.statement!!.executeQuery()
                if (subject.group.query.expression is IUpdateQueryExpression) subject.effectedRows = subject.statement!!.executeBatch()
            } catch (exception: Exception) {
                subject.exception = exception
                return
            }
        }
    }
}
