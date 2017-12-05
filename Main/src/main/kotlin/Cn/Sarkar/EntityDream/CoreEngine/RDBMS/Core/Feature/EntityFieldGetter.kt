/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */


package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GetEntityFieldValueSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.getDefaultValue
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object EntityFieldGetter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.EntityFieldGetter")
    override val info: FeatureInfo by lazy { FeatureInfo(
            "EntityFieldGetter",
            "بۇ Entity نىڭ مەلۇم بىر خاسلىقىنىڭ قىممىتىنى ئالغاندا مۇشۇ قىستۇرما شۇ ئىقتىدارنى ئەمەلگە ئاشۇرىدۇ",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is GetEntityFieldValueSubject)
        {
            if (subject.entity.values == null)
            {
                subject.value = subject.column.getDefaultValue()!!
                return
            }

            subject.value = subject.entity.values!![subject.column] ?: subject.column.getDefaultValue()!!
        }
    }
}