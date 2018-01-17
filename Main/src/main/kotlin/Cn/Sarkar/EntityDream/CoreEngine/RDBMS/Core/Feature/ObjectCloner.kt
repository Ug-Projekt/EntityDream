/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/17/18
Time: 10:58 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.cloneObjects
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.ObjectCloneSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object ObjectCloner : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.ObjectCloner") }
    override val info: FeatureInfo by lazy { FeatureInfo("Object Cloner", "Clone Any Objects", "Sarkar Software Technologys", "yeganaaa", 1, "v0.1") }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {

        if (subject is ObjectCloneSubject) {
            val input = subject.SourceObject
            subject.ClonedObject = when (input) {
                /*  SelectQueryExpression  */
                is SelectQueryExpression -> SelectQueryExpression(
                        input.table,
                        input.Select.cloneObjects(featureContext),
                        input.From.cloneObjects(featureContext),
                        input.Where?.cloneObjects(featureContext),
                        input.GroupBy?.cloneObjects(featureContext),
                        input.Having?.cloneObjects(featureContext),
                        input.OrderBy?.cloneObjects(featureContext)
                )
                /*  Select  */
                is Select -> Select(*input.selectors.toTypedArray(), top = input.top, offset = input.offset, distinct = input.distinct)

                /*  From  */
                is From -> From(input.fromSelector.cloneObjects(featureContext), *input.join.toTypedArray())
                is FromTable -> FromTable(input.tableName)
                is FromQueryContainer -> FromQueryContainer(input.select.cloneObjects(featureContext), input.aliasName)

                /*  Where  */
                is Where -> Where(input.condition.cloneObjects(featureContext))

                is Equal -> Equal({input.first()}, {input.last()})
                is NotEqual -> NotEqual({input.first()}, {input.last()})
                is GreaterThen -> GreaterThen({input.first()}, {input.last()})
                is LessThen -> LessThen({input.first()}, {input.last()})
                is GreaterOrEqualThen -> GreaterOrEqualThen({input.first()}, {input.last()})
                is LessOrEqualThen -> LessOrEqualThen({input.first()}, {input.last()})
                is Between -> Between({input.first()}, {input.from()}, {input.to()})
                is NotBetween -> NotBetween({input.first()}, {input.from()}, {input.to()})
                is Like -> Like({input.first()}, {input.last()})
                is NotLike -> NotLike({input.first()}, {input.last()})
                is In -> In({input.first()}, {input.others()})
                is NotIn -> NotIn({input.first()}, {input.others()})
                is And -> And(*input.conditions.toTypedArray())
                is Or -> Or(*input.conditions.toTypedArray())

                /*  GroupBy  */
                is GroupBy -> GroupBy(input.ColumnName)

                /*  Having  */
                is Having -> Having(input.function.cloneObjects(featureContext), input.condition.cloneObjects(featureContext))

            /*Aggregate Functions*/
                is Avg -> Avg(input.Value.cloneObjects(featureContext))

                is FuncFromColumn -> FuncFromColumn({input.Value()})
                is FuncFromFunction -> FuncFromFunction({input.Value()})

                is Count -> Count(input.Value.cloneObjects(featureContext), input.Distinct)

                is Max -> Max(input.Value.cloneObjects(featureContext))
                is Min -> Min(input.Value.cloneObjects(featureContext))
                is Sum -> Sum(input.Value.cloneObjects(featureContext))
            /*Scalar Functions*/
                is Format -> Format(input.Value.cloneObjects(featureContext), input.Format)
                is LCase -> LCase(input.Value.cloneObjects(featureContext))
                is UCase -> UCase(input.Value.cloneObjects(featureContext))
                is Len -> Len(input.Value.cloneObjects(featureContext))
                is Mid -> Mid(input.Value.cloneObjects(featureContext), input.From, input.Length)
                is Round -> Round(input.Value.cloneObjects(featureContext), input.Decimals)
                is Now -> input

                /*  OrderBy  */
                is OrderBy -> OrderBy(input.Name, input.orderMode)

                else -> null
            }
        }
    }
}

