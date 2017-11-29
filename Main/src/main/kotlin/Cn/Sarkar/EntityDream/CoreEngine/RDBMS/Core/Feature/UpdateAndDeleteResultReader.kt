package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.UpdateAndDeleteResultReaderSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object UpdateAndDeleteResultReader : PipeLineFeature<UpdateAndDeleteResultReaderSubject, IDataContext, Unit>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDreams.Core.UpdateAndDeleteResultReader")
    override val info: FeatureInfo by lazy { FeatureInfo(
            "UpdateAndDeleteResultReader",
            "يىڭىلاش ۋە ئۆچۈرۈش مەشغۇلاتىدىن قايىتقان ئۇچۇرنى ئوقۇيدۇ",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<UpdateAndDeleteResultReaderSubject, IDataContext>.onExecute(subject: UpdateAndDeleteResultReaderSubject) {
        if (subject is UpdateAndDeleteResultReaderSubject)
        {
            subject.EffectedRows = subject.executionSubject.effectedRows
        }
    }
}