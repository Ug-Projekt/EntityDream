/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/13/18
Time: 6:07 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Char
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Double
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Float
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Int
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.CorePipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects.TypeAdapterSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineContext
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeatureMetaData

object DataTypeAdapter : PipeLineFeature<IPipeLineSubject, IDataContext>() {
    override val getMetaData: PipeLineFeatureMetaData by lazy { PipeLineFeatureMetaData(CorePipeLine.process, "Cn.Sarkar.EntityDream.CoreEngine.RDBMS.SQLite.Core.Feature.DataTypeAdapter") }
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

                is TinyInt -> "TINYINT"
                is SmallInt -> "SMALLINT"
                is MediumInt -> "MEDIUMINT"
                is Int -> "INTEGER"
                is BigInt -> "BIGINT"

                is Float -> "FLOAT"
                is Double -> "DOUBLE"
                is Decimal -> "DECIMAL"
                is Money -> "DECIMAL"

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