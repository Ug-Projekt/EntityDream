/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 12:47 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.DataReaderSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData
import org.joda.time.DateTime
import java.sql.ResultSet
import java.sql.Time
import java.util.Date

object DataReader : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Feature.DataReader") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "Result Set Reader",
                "Result Set Reader",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is DataReaderSubject) {
            subject.Data = subject.ResultSet.readResult(subject.FieldName, subject.DataType)
        }
    }

    private fun ResultSet.readResult(fieldName: String, dataType: IDBDataType<*>) : Any? = when(dataType){

        is TinyInt, is SmallInt, is MediumInt, is DBInt, is BigInt -> this.getInt(fieldName)

        is DBFloat -> this.getFloat(fieldName)
        is DBDouble -> this.getDouble(fieldName)
        is Decimal, is Money -> this.getBigDecimal(fieldName).toDouble()

        is Binary, is MediumBinary, is LongBinary -> this.getBlob(fieldName).binaryStream.readBytes()

        is Date -> DateTime(this.getDate(fieldName))
        is Time -> DateTime(this.getTime(fieldName))
        is DBDateTime -> DateTime(this.getTimestamp(fieldName))
        is TimeStamp -> DateTime(this.getTimestamp(fieldName))

        is Bool -> this.getBoolean(fieldName)

        is TinyText, is MediumText, is Text, is LongText -> this.getString(fieldName)

        is DBChar, is NChar, is VarChar, is NVarChar ->this.getString(fieldName)

        else -> null
    }
}