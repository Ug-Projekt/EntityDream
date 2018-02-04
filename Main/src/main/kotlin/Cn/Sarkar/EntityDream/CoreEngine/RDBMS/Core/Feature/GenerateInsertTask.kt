/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/10/17
Time: 11:19 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.autoIncrementColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.generateRandomUnique
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.uniqueKey
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.GenerateInsertTaskSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertInto
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.Values
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object GenerateInsertTask : PipeLineFeature<IPipeLineSubject, IDataContext>(){



    override val getMetaData: PipeLineFeatureMetaData by lazy {
        PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.GenerateInsertTask")
    }
    override val info: FeatureInfo by lazy { FeatureInfo(
            "Insert Task Generator",
            "Insert Task Generator",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is GenerateInsertTaskSubject)
        {
            val entity = subject.entity
            entity.DataContext = subject.context
            entity.Table = subject.table

            val incrementField = subject.table.autoIncrementColumn

            var kvs = entity.Table.Columns.filter { entity.values!![it] != null }


            if (incrementField != null) { //ئەگەر ئاپتوماتىك ئاينىيدىغان خاسلىقى بولۇپ قالسا
                entity.generateRandomUnique() //بىردىنبىر بولغان پەرىقلەندۈرۈش كودى چىقىشى يەنى نۆۋەتتىكى ئىلىمىنىت قوشۇلۇپ بولغاندىن كىيىن مۇشۇ ئىلىمىنىتنى مۇشۇ بىرىدنبىر بولغان قىممەت ئارقىلىق ئىزدەپ تاپىمىز
                entity.DataContext.insertedIntities.put(entity.uniqueKey, entity)

                kvs = kvs.filter { it != incrementField } //ئاپتوماتىك ئاينىيدىغان خاسلىقىنى ساندانغا كىرگۈزىلىدىغان ئۇچۇرلار قاتارىدىن چىقىرۋەتسەك بولىدۇ
            }


            val expr = InsertQueryExpression(
                    entity.uniqueKey,
                    entity.Table,
                    InsertInto(entity.Table.TableName, kvs.map { it.ColumnName }.toTypedArray()),
                    Values(kvs.map { MappedParameter(entity.values!![it]!!, it.DataType) }.toTypedArray())
            )

            entity.DataContext.insertTasks.put(entity.uniqueKey, expr)
        }
    }
}





