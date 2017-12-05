/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */


package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.KeyValuePair
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GenerateUpdateTaskSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.SetEntityFieldValueSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object EntityFieldSetter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.EntityFieldSetter")
    override val info: FeatureInfo by lazy { FeatureInfo(
            "EntityFieldSetter",
            "بۇ Entity نىڭ مەلۇم بىر خاسلىقىغا قىممەت بەرگەندە مۇشۇ قىستۇرما شۇ ئىقتىدارنى ئەمەلگە ئاشۇرىدۇ",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is SetEntityFieldValueSubject) {
            if (subject.value == subject.entity.values!![subject.column]) return
            val cpl = featureContext.clonedPipeLine
            val result = featureContext.execute(cpl, GenerateUpdateTaskSubject(subject.entity, KeyValuePair(subject.column.fullColumnName, subject.value)))
        }
    }
}
