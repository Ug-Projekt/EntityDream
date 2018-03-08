package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.DataWriterSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.QueryExecutionSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.IUpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import java.sql.PreparedStatement

object QueryExecuter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
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
                subject.statement = subject.connection.prepareStatement(subject.group.query.sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)
                val isSingleContent = subject.group.content.size == 1


                if (!isSingleContent) {
                    subject.group.content.forEach {
                        it.parameters.forEachIndexed { i, parameter -> subject.statement!!.WriteParameters(featureContext, i + 1, parameter) }
                        subject.statement!!.addBatch()
                    }
                } else {

                    subject.group.content.single().parameters.forEachIndexed { index, parameter -> subject.statement!!.WriteParameters(featureContext, index + 1, parameter) }
                }

                when (subject.group.query.expression) {
                    is ISelectQueryExpression -> subject.resultSet = subject.statement!!.executeQuery()

                    is IUpdateQueryExpression -> subject.effectedRows = if (isSingleContent) intArrayOf(subject.statement!!.executeUpdate()) else subject.statement!!.executeBatch()
                    else -> throw Exception("""Query must be instanceof ISelectQueryExpression or IUpdateQueryExpression
                        چوقۇم Query IUpdateQueryExpression ياكى IUpdateQueryExpression غا ۋارىسلىق قىلىشى كىرەك
                    """.trimMargin())
                }
            } catch (exception: Exception) {
                subject.exception = exception
                exception.printStackTrace()
                return
            }
        }
    }

    private fun PreparedStatement.WriteParameters(context: IDataContext, index: Int, parameter: SqlParameter) {
        context.execute(context.clonedPipeLine, DataWriterSubject(this, index, parameter))
    }
}
