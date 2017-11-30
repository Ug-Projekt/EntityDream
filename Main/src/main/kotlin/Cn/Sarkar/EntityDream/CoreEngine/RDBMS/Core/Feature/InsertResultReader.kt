/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/30/17
Time: 4:47 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.IDItem
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.InsertResultSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object InsertResultReader : PipeLineFeature<IPipeLineSubject, IDataContext, Unit>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.InsertResultReader") }
    override val info: FeatureInfo by lazy { FeatureInfo(
            "Insert Result Reader",
            "يىڭى قىستۇرغان ئۇچۇرلارنىڭ ID سىنى ئوقۇغۇچ",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is InsertResultSubject)
        {
            subject.result.group
            val ids = ArrayList<IDItem>()
            val keys = subject.result.statement!!.generatedKeys
            while (keys.next())
            {
                ids.add( IDItem(subject.result., keys.getInt(1)))
            }
        }
    }
}