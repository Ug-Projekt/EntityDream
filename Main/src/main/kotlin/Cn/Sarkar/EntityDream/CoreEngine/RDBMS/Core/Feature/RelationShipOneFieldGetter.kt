/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/23/17
Time: 9:08 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.simpleWhereCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityRelationShipOneFieldSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object RelationShipOneFieldGetter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.RelationShipOneFieldGetter") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "RelationShipOneFieldGetter",
                "RelationShipOneFieldGetter",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is GetEntityRelationShipOneFieldSubject<*>) {
            if (subject.delegateConnector.cachedValue != null) {
                subject.selectResult = subject.delegateConnector.cachedValue
                return
            }
            val k = subject.relationShipColumn.ForeignKey!!.fullColumnName
            val v = subject.thisRefEntity.values!![subject.relationShipColumn]!!
            val condition = Equal({k}, { MappedParameter(v, subject.relationShipColumn.DataType) })
            val entityCollection = QueriableCollection(subject.thisRefEntity.DataContext, subject.relationShipColumn.ForeignKey!!.Table, condition, { subject.entityGenerator(subject.thisRefEntity.DataContext) })
            subject.selectResult = entityCollection.single()
            subject.delegateConnector.cachedValue = subject.selectResult
        }
    }
}

