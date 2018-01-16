/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 5:54 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.DataWriterSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.NormalParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import org.joda.time.DateTime
import java.math.BigDecimal
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.Time
import java.sql.Timestamp

object DataWriter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.DataWriter") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "PreparedStatement Aurgument Writer",
                "Prepared Statement Argument Writer",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is DataWriterSubject)
        {
            subject.Statement.WriteParameters(subject.Index, subject.Parameter)
        }
    }

    private fun PreparedStatement.WriteParameters(index: Int, parameter: SqlParameter) {
        when(parameter) {
            is NormalParameter -> {
                val parameterValue = parameter.Value
                when (parameterValue) {
                    is String -> this.setString(index, parameterValue)
                    is Boolean -> this.setBoolean(index, parameterValue)
                    is Byte -> this.setByte(index, parameterValue)
                    is Short -> this.setShort(index, parameterValue)
                    is Int -> this.setInt(index, parameterValue)
                    is Long -> this.setLong(index, parameterValue)
                    is Float -> this.setFloat(index, parameterValue)
                    is Double -> this.setDouble(index, parameterValue)
                    is BigDecimal -> this.setBigDecimal(index, parameterValue)
                    is ByteArray -> this.setBlob(index, java.io.ByteArrayInputStream(parameterValue))
//                    is InputStream -> this.setBlob(index, parameterValue)
                    is Date -> this.setDate(index, parameterValue)
                    is Time -> this.setTime(index, parameterValue)
                    is Timestamp -> this.setTimestamp(index, parameterValue)
                    is DateTime -> this.setTimestamp(index, java.sql.Timestamp(parameterValue.millis))

                    else -> throw Exception("قوللىمايدىغان پارامىتىر تىپى...")
                }
            }
            is MappedParameter -> {
                when (parameter.DataType) {
                    is TinyInt, is SmallInt, is MediumInt, is DBInt, is BigInt -> this.setInt(index, parameter.Value as Int)

                    is DBFloat -> this.setFloat(index, parameter.Value as Float)
                    is DBDouble -> this.setDouble(index, parameter.Value as Double)
                    is Decimal, is Money -> this.setBigDecimal(index, java.math.BigDecimal(parameter.Value.toString()))

                    is Binary, is MediumBinary, is LongBinary -> this.setBlob(index, java.io.ByteArrayInputStream(parameter.Value as ByteArray))

                    is DBDate -> this.setDate(index, java.sql.Date((parameter.Value as DateTime).millis))
                    is DBTime -> this.setTime(index, java.sql.Time((parameter.Value as DateTime).millis))
                    is DBDateTime -> this.setTimestamp(index, java.sql.Timestamp((parameter.Value as DateTime).millis))
                    is TimeStamp -> this.setTimestamp(index, java.sql.Timestamp((parameter.Value as DateTime).millis))

                    is Bool -> this.setBoolean(index, parameter.Value as Boolean)

                    is TinyText, is MediumText, is Text, is LongText -> this.setString(index, parameter.Value as String)

                    is DBChar, is NChar, is VarChar, is NVarChar -> this.setString(index, parameter.Value as String)
                }
            }
        }
    }
}


