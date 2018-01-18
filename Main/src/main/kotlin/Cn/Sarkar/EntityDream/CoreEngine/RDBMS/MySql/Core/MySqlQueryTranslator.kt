/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 1:57 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Binary
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.General.Text
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBPlainScaledDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBScaledType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions.toLocalDBDmlString
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.FuncFromWhat
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.CreateTable.CreateTableExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.NormalParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.Subject.NamingRuleSubject
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.clonedPipeLine
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.execute
import org.joda.time.DateTime
import sun.misc.BASE64Encoder
import java.security.MessageDigest
import java.util.*

class MySqlQueryTranslator(override val DataContext: IDataContext) : IQueryTranslator {
    val params = ArrayList<SqlParameter>()

    val md5Algorithm by lazy { MessageDigest.getInstance("MD5") }
    fun encryptWithMD5(sourceString: String): String {
        return BASE64Encoder().encode(md5Algorithm.digest(sourceString.toByteArray()))
    }

    fun IDBFunction.renderToString() : String
    {
        val excep = Exception("""
            بۇ تىپنى تەرجىمان قوللىمايدۇ
            This translator is not supported this type.
            此翻译器不支持该类型
        """.trimIndent())

        var retv = ""

        fun FuncFromWhat.recursiveRender() : String = this.run {
            when(this)
            {
                is FuncFromColumn -> this.Value()
                is FuncFromFunction -> this.Value().renderToString()
                else-> throw excep
            }
        }

        when (this)
        {
            /*Aggregate Functions*/
            is Avg -> retv = "AVG(${this.Value.recursiveRender()})"

            is Count -> retv = "COUNT(${if (this.Distinct) "DISTINCT " else ""}${this.Value.recursiveRender()})"
            is Max -> retv = "MAX(${this.Value.recursiveRender()})"
            is Min -> retv = "MIN(${this.Value.recursiveRender()})"
            is Sum -> retv = "SUM(${this.Value.recursiveRender()})"
            /*Scalar Functions*/
            is Format -> retv = "FORMAT(${this.Value.recursiveRender()}, ${this.Format})"
            is LCase -> retv = "LCASE(${this.Value.recursiveRender()})"
            is UCase -> retv = "UCASE(${this.Value.recursiveRender()})"
            is Len -> retv = "LEN(${this.Value.recursiveRender()})"
            is Mid -> retv = "MID(${this.Value.recursiveRender()}, ${this.From}${this.Length.run { if (this == null) "" else ", ${this}" }})"
            is Round -> retv = "ROUND(${this.Value.recursiveRender()}, ${this.Decimals})"
            is Now -> retv = "NOW()"
        }
        return retv
    }

    fun FromWhat.renderToString() : String {
        return when (this) {
            is FromColumn -> "${this.SourceColumnName}${this.AliasName.run { if (this == null) "" else " AS ${this}" }}"
            is FromFunction -> "${this.Function.renderToString()}${this.AliasName.run { if (this == null) "" else " AS ${this}" }}"
            else -> error("تەرجىمان نۆۋەتتىكى تىپنى تەرجىمە قىلىش ئىقتىدارىنى تەمىنلىمىگەن، تەرجىماننىڭ نەشىرىنى ئۆستۈرۈڭ ياكى نۆۋەتتىكى قوللىمايدىغان تىپنى قوللاش ئىقتىدارىنى قوشۇڭ!")
        }
    }

    fun <T> T.toSqlString() : String = when(this)
    {
        is String -> "'$this'"
        is DateTime -> "'${this.toString("yyyy-MM-dd hh:mm:ss")}'"
        is Date -> "'$this'"
        is Int, is Float, is Double -> this.toString()
        else -> this.toString()
    }
    fun SqlParameter.renderToString(): String {
        params.add(this)
        return "?"
    }

    fun recursiveFromBlock(selector: FromSelector): String {
        val innerBuffer = StringBuffer()
        when (selector) {
            is FromTable -> {
                innerBuffer.append("${selector.tableName} ")
            }
            is FromQueryContainer -> {
                innerBuffer.append("(${Translate(selector.select)}) ${selector.aliasName} ")
            }
        }
        return innerBuffer.toString()
    }

    fun recursiveWhereBlock(where: WhereItemCondition, isTrunk: Boolean = false): String {
        fun SqlParameter.renderHere() = this.run { params.add(this);"?" }
        return when (where) {
            is And -> {
                if (isTrunk) where.conditions.joinToString(separator = " AND ") { recursiveWhereBlock(it) } else "(${where.conditions.joinToString(separator = " AND ") { recursiveWhereBlock(it) }}) "
            }
            is Or -> {
                if (isTrunk) where.conditions.joinToString(separator = " OR ") { recursiveWhereBlock(it) } else "(${where.conditions.joinToString(separator = " OR ") { recursiveWhereBlock(it) }}) "
            }
            is Equal -> {
                "${where.first()} = ${where.last().renderHere()} "
            }
            is NotEqual -> {
                "${where.first()} != ${where.last().renderHere()} "
            }
            is GreaterThen -> {
                "${where.first()} > ${where.last().renderHere()} "
            }
            is LessThen -> {
                "${where.first()} < ${where.last().renderHere()} "
            }
            is GreaterOrEqualThen -> {
                "${where.first()} <= ${where.last().renderHere()} "
            }
            is LessOrEqualThen -> {
                "${where.first()} >= ${where.last().renderHere()} "
            }
            is Between -> {
                "${where.first()} BETWEEN ${where.from().renderHere()} AND ${where.to().renderHere()} "
            }
            is NotBetween -> {
                params.add(where.from())
                params.add(where.to())
                "${where.first()} NOT BETWEEN ${where.from().renderHere()} AND ${where.to().renderHere()} "
            }
            is Like -> {
                "${where.first()} LIKE ${where.last().renderHere()} "
            }
            is NotLike -> {
                "${where.first()} NOT LIKE ${where.last().renderHere()} "
            }
            is In -> {
                params.add(NormalParameter(where.others().joinToString{ it.toSqlString() }))
                "${where.first()} IN (?) "
            }
            is NotIn -> {
                params.add(NormalParameter(where.others().joinToString{ it.toSqlString() }))
                "${where.first()} NOT IN (?) "
            }
            else -> throw Exception("Not Supported!قوللىمايدۇ")
        }
    }

    fun IDBTable.generateConstraintDmls() : String
    {
        val gen = DataContext.execute(DataContext.clonedPipeLine, NamingRuleSubject())
        val buffer = StringBuilder()

        val constraints = ArrayList<String>()

        if (gen.IndexNamingRules == null || gen.PrimaryKeyNamingRules == null || gen.UniqueNamingRules == null)
            throw Exception("Not found naming rulesئىسىم ھاسىللىغۇچنى تاپالمىدى، 找不到命名生成器，")

        if (PrimaryKey.isNotEmpty()) {
            buffer.append(",\n")
            val name = gen.PrimaryKeyNamingRules!!(this, PrimaryKey)
            constraints.add("CONSTRAINT $name PRIMARY KEY (${this.PrimaryKey.joinToString { it.ColumnName }})")
        }

//        Columns.filter { !it.IsPrimaryKey && it.Index.isIndex && !it.DataType is Text && !it.DataType is Binary }.groupBy { it.Index.groupIndex }.forEach { g, v ->
//            val name = gen.UniqueNamingRules!!(this, v.toTypedArray())
//            buffer.append("INDEX $name ")
//        }

        constraints.addAll(Columns.filter { !it.IsPrimaryKey && it.Unique.isUnique }.groupBy { it.Unique.uniqueGroupIndex }.map {
            val name = gen.UniqueNamingRules!!(this, it.value.toTypedArray())
            "CONSTRAINT $name UNIQUE (${it.value.joinToString { it.ColumnName }})"
        })
        buffer.append(constraints.joinToString(separator = ",\n") { it })

        return buffer.toString()
    }

    fun IDBTable.renderToCreateTableSqlString() = """${this.TableName} (
${this.Columns.joinToString(",\n") { it.renderToCreateTableSqlString() }}

${this.generateConstraintDmls()}
)""".trimIndent()

    fun IDBColumn<*>.renderToCreateTableSqlString(): String {
        val buffer = StringBuffer()
        buffer.append("$ColumnName ")
        val type = this.DataType.toLocalDBDmlString(DataContext)
        buffer.append(type)

        buffer.append(if (this.NotNull) " NOT NULL " else " NULL ")
        buffer.append(if (this.AutoIncrement.autoIncrement) "AUTO_INCREMENT " else "")
        if (this.NotNull && this.getDefaultValue() != null && !this.AutoIncrement.autoIncrement) {
            if (!(this.DataType.DefaultValue is String && this.getDefaultValue() == ""))
                buffer.append("DEFAULT ${this.getDefaultValue().toSqlString()} ")
        }
        buffer.append(if (this.Comment != "") "COMMENT '${this.Comment}' " else "")
        return buffer.toString()
    }

    override fun Translate(expression: IQueryExpression): TranslateResult {
        val buffer = StringBuffer()

        when (expression) {
            is ISelectQueryExpression -> {

                buffer.append("SELECT ")
                buffer.append(expression.Select.selectors.joinToString { it.renderToString() })
                buffer.append(" FROM ")
                buffer.append(recursiveFromBlock(expression.From.fromSelector))
                buffer.append(expression.From.join.run {
                    var retv = ""
                    if (this.isNotEmpty()) {
                        this.forEach {
                            when (it.joinMode) {
                                JoinMode.FullJoin -> retv += "JOIN "
                                JoinMode.RightJoin -> retv += "RIGHT JOIN "
                                JoinMode.LeftJoin -> retv += "LEFT JOIN "
                                JoinMode.InnerJoin -> retv += "INNER JOIN "
                            }
                            retv += "${recursiveFromBlock(it.fromSelector)} ON ${recursiveWhereBlock(it.on)}"
                        }
                    }
                    retv
                })
                buffer.append(expression.Where?.condition.run { if (this == null) "" else "WHERE ${recursiveWhereBlock(expression.Where!!.condition, true)}" })
                buffer.append(expression.GroupBy.run { if (this == null) "" else "GROUP BY ${ColumnName} "})
                buffer.append(expression.Having.run {if (this == null) "" else "HAVING ${function.renderToString()} ${recursiveWhereBlock(condition)} "})
                buffer.append(expression.OrderBy.run { if (this == null) "" else "ORDER BY ${this.Name} ${this.orderMode.Name} " })

                if (expression.Select.offset != null && expression.Select.top == null) expression.Select.top = 0

                buffer.append(expression.Select.top.run { if (this == null) "" else "LIMIT $this" })
                buffer.append(expression.Select.offset.run { if (this == null) "" else ", $this" })
                buffer.append(";")
//                println(buffer)
            }
            is UpdateQueryExpression -> {

                buffer.append("UPDATE ${expression.update.TableName} SET ${expression.set.Values.joinToString { "${it.key}=${it.value.run { this.renderToString() }}" }} ")
                buffer.append("WHERE ${recursiveWhereBlock(expression.where.condition)}")
                buffer.append(";")
//                println(buffer)
            }
            is InsertQueryExpression -> {
                buffer.append("INSERT INTO ${expression.insertInto.TableName} (${expression.insertInto.Columns.joinToString { it }}) VALUES (${expression.values.Values.joinToString { it.renderToString() }})")
                buffer.append(";")
//                buffer.append("Select @@identity")
//                buffer.append(";")
            }
            is DeleteQueryExpression -> {
                buffer.append("DELETE FROM ${expression.deleteFrom.TableName} WHERE ${recursiveWhereBlock(expression.where.condition)}")
                buffer.append(";")
            }
            is CreateTableExpression -> {
                buffer.append("CREATE TABLE IF NOT EXISTS ")
                buffer.append(expression.table.renderToCreateTableSqlString())
                buffer.append(";")
            }
        }
        val query = buffer.toString()
        var fullQuery = query
        params.forEach {
            fullQuery = fullQuery.replaceFirst("?", it.Value.toSqlString())
        }
        val retv = TranslateResult(encryptWithMD5(query), query, params.toList(), fullQuery, expression)
        params.clear()
        return retv
    }
}


