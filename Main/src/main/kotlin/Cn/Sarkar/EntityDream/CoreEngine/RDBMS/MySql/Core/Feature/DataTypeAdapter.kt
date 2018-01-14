/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/13/18
Time: 6:07 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Char
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Double
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Float
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainScaledDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBScaledType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TypeAdapterSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object DataTypeAdapter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Feature.DataTypeAdapter") }
    override val info: FeatureInfo by lazy {
        FeatureInfo(
                "DataTypeAdapter",
                "Kotlin and each database data type adapter",
                "Sarkar Software Technologys",
                "yeganaaa",
                1,
                "v0.1"
        )
    }

    override fun PipeLineContext<IPipeLineSubject, IDataContext>.onExecute(subject: IPipeLineSubject) {
        if (subject is TypeAdapterSubject) {
            val input = subject.Input

            subject.OutPutString = when (input) {

                is TinyInt -> "TINYINT${if (input.Unsigned) " UNSIGNED" else ""}"
                is SmallInt -> "SMALLINT${if (input.Unsigned) " UNSIGNED" else ""}"
                is MediumInt -> "MEDIUMINT${if (input.Unsigned) " UNSIGNED" else ""}"
                is Int -> "INT${if (input.Unsigned) " UNSIGNED" else ""}"
                is BigInt -> "BIGINT${if (input.Unsigned) " UNSIGNED" else ""}"

                is Float -> "FLOAT${if (input.Unsigned) " UNSIGNED" else ""}"
                is Double -> "DOUBLE${if (input.Unsigned) " UNSIGNED" else ""}"
                is Decimal -> "DECIMAL${if (input.Unsigned) " UNSIGNED" else ""}"
                is Money -> "DECIMAL${if (input.Unsigned) " UNSIGNED" else ""}"

                is Binary -> "BLOB"
                is MediumBinary -> "MEDIUMBLOB"
                is LongBinary -> "LONGBLOB"

                is Date -> "DATE"
                is Time -> "TIME"
                is DateTime -> "DATETIME"
                is TimeStamp -> "TIMESTAMP"

                is Bool -> "BOOLEAN"

                is TinyText -> "TINYTEXT"
                is MediumText -> "MEDIUMTEXT"
                is Text -> "TEXT"
                is LongText -> "LONGTEXT"

                is Char -> "CHAR(${input.ScaleValue})"
                is NChar -> "NCHAR(${input.ScaleValue})"
                is VarChar -> "VARCHAR(${input.ScaleValue})"
                is NVarChar -> "NVARCHAR(${input.ScaleValue})"

                else -> null
            }
        }
    }
}