/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 8:34 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.SelectResultSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.FromColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.FromFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.validName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.readResult
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ValuesCacheItem
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object SelectResultReader : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.SelectResultReader") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "Select Result Reader",
                "Select Result Reader",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is SelectResultSubject) {
            val columns = (subject.executionSubject.group.query.expression as ISelectQueryExpression).Select.selectors
            val result = subject.executionSubject.statement!!.resultSet
            if (result == null) return
            while (result.next()) {
                val row = ValuesCacheItem()
                columns.forEach {
                    val name = when (it)
                    {
                        is FromColumn -> it.validName
                        is FromFunction -> it.AliasName
                        else -> throw Exception("Not Supported this 'Select'!")
                    }

                    val value = result.readResult(name, it.DefaultValue)
                    row.put(name, value)
                }
                subject.values.add(row)
            }

        }
    }
}