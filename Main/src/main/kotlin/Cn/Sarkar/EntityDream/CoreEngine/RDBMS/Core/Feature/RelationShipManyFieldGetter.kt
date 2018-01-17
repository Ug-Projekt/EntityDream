/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/23/17
Time: 9:08 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityRelationShipManyFieldSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueriableCollection
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object RelationShipManyFieldGetter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.RelationShipManyFieldGetter") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "RelationShipManyFieldGetter",
                "RelationShipManyFieldGetter",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is GetEntityRelationShipManyFieldSubject<*>) {
            val k = subject.relationShipColumn
            val v = subject.thisRefEntity.values!![subject.relationShipColumn.ForeignKey!!]!!


            val condition = Equal({k.fullColumnName}, { MappedParameter(v, k.DataType) })
            val gen = {subject.entityGenerator(subject.thisRefEntity.DataContext)}

            subject.selectResult = QueriableCollection(subject.thisRefEntity.DataContext, subject.relationShipColumn.Table, condition, {gen()})
        }
    }
}

