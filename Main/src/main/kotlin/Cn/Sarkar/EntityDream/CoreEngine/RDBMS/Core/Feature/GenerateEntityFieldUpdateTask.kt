/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/2/17
Time: 11:57 PM
 */


package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.uniqueKey
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.simpleWhereCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GenerateEntityFieldUpdateTaskSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Where
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.Set
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.Update
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object GenerateEntityFieldUpdateTask : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.GenerateEntityFieldUpdateTask")
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "GenerateEntityFieldUpdateTask",
                "ھەر بىر خاسلىققا قىممەت بىرىلگەن دەقىقىدە يىڭىلاش ۋەزىپىسى ھاسىل قىلىدۇ ۋە ۋەزىپە تىزىملىكىگە قوشىدۇ",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is GenerateEntityFieldUpdateTaskSubject) {
            var updateExpression = featureContext.updateTasks[subject.entity.uniqueKey]
            if (updateExpression == null) {
                updateExpression = UpdateQueryExpression(subject.entity, subject.entity.Table, Update(subject.entity.Table.TableName), Set(), Where(subject.entity.simpleWhereCondition))
                featureContext.updateTasks.put(subject.entity.uniqueKey, updateExpression)
            }

            var existingValues = updateExpression.set.Values.singleOrNull { it.key == subject.values.key }
            if (existingValues == null) {
                existingValues = subject.values
                updateExpression.set.Values.add(existingValues)
            }

            existingValues.value = subject.values.value
        }
    }
}
