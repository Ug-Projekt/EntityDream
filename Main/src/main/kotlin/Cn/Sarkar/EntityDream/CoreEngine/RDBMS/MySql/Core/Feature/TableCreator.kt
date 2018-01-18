/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/11/18
Time: 4:53 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.CreateTableSubjet
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TranslationSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Subject.NamingRuleSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object TableCreator : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Feature.TableCreator") }
    override val info: FeatureInfo by lazy { FeatureInfo(
            "Table Creator",
            "Create new table",
            "Sarkar Software Technologys",
            "yeganaaa",
            1,
            "v0.1"
    ) }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is CreateTableSubjet)
        {
            try {

                val cpl = featureContext.clonedPipeLine
                val statement = featureContext.connection.createStatement()

                subject.expressions.forEach {
                    val result = featureContext.execute(cpl, TranslationSubject(it))
                   subject.size += statement.executeUpdate(result.translationResult!!.fullSqlQuery)
                }
            }
            catch (except: Exception)
            {
                subject.exception = except
            }
        }

        if (subject is NamingRuleSubject)
        {
            subject.PrimaryKeyNamingRules = { "PrimaryKey__${it.joinToString(separator = "_") { it.ColumnName }}" }
            subject.UniqueNamingRules = { "Unique__${it.joinToString(separator = "_") { it.ColumnName }}" }
            subject.IndexNamingRules = { "Index__${it.joinToString(separator = "_") { it.ColumnName }}" }
        }
    }
}
