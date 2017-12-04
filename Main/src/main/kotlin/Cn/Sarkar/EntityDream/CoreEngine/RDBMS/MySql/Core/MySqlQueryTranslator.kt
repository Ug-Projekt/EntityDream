/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 1:57 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Aggregate.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.IDBFunction
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Function.Scalar.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.ISelectQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Insert.InsertQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult
import sun.misc.BASE64Encoder
import java.security.MessageDigest

class MySqlQueryTranslator : IQueryTranslator {
    val params = ArrayList<Any>()

    val md5Algorithm by lazy { MessageDigest.getInstance("MD5") }
    fun encryptWithMD5(sourceString: String): String {
        return BASE64Encoder().encode(md5Algorithm.digest(sourceString.toByteArray()))
    }

    fun IDBFunction.renderToString() : String
    {
        var retv = ""
        when (this)
        {
            /*Aggregate Functions*/
            is Avg -> retv = "AVG(${this.Value()}) "
            is Count -> retv = "COUNT(${if (!this.Distinct) "" else "DISTINCT "}${this.Value()}) "
            is Max -> retv = "MAX(${this.Value()}) "
            is Min -> retv = "MIN(${this.Value()}) "
            is Sum -> retv = "SUM(${this.Value()}) "
            /*Scalar Functions*/
            is Format -> retv = "FORMAT(${this.Value()}, ${this.Format}) "
            is LCase -> retv = "LCASE(${this.Value()}) "
            is UCase -> retv = "UCASE(${this.Value()}) "
            is Len -> retv = "LEN(${this.Value()}) "
            is Mid -> retv = "MID(${this.Value()}, ${this.From}${this.Length.run { if (this == null) "" else ", ${this}" }}) "
            is Round -> retv = "ROUND(${this.Value()}, ${this.Decimals}) "
            is Now -> retv = "NOW() "
        }
        return retv
    }

    fun FromWhat.renderToString() : String {
        return when (this) {
            is FromColumn -> "${this.SourceName}${this.AliasName.run { if (this == null) "" else " AS ${this}" }}"
            is FromFunction -> "${this.Function.renderToString()}${this.AliasName.run { if (this == null) "" else " AS ${this}" }}"
            else -> error("تەرجىمان نۆۋەتتىكى تىپنى تەرجىمە قىلىش ئىقتىدارىنى تەمىنلىمىگەن، تەرجىماننىڭ نەشىرىنى ئۆستۈرۈڭ ياكى نۆۋەتتىكى قوللىمايدىغان تىپنى قوللاش ئىقتىدارىنى قوشۇڭ!")
        }
    }

    fun <T> T.toSqlString() : String = when(this)
    {
        is String -> "'$this'"
        is Int, is Float, is Double -> this.toString()
        else -> this.toString()
    }
    fun Any.renderToString(): String {
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
        return when (where) {
            is And -> {
                if (isTrunk) where.conditions.joinToString(separator = " AND ") { recursiveWhereBlock(it) } else "(${where.conditions.joinToString(separator = " AND ") { recursiveWhereBlock(it) }}) "
            }
            is Or -> {
                if (isTrunk) where.conditions.joinToString(separator = " OR ") { recursiveWhereBlock(it) } else "(${where.conditions.joinToString(separator = " OR ") { recursiveWhereBlock(it) }}) "
            }
            is Equal -> {
                params.add(where.last())
                "${where.first()} = ? "
            }
            is NotEqual -> {
                params.add(where.last())
                "${where.first()} != ? "
            }
            is GreaterThen -> {
                params.add(where.last())
                "${where.first()} > ? "
            }
            is LessThen -> {
                params.add(where.last())
                "${where.first()} < ? "
            }
            is GreaterOrEqualThen -> {
                params.add(where.last())
                "${where.first()} <= ? "
            }
            is LessOrEqualThen -> {
                params.add(where.last())
                "${where.first()} >= ? "
            }
            is Between -> {
                params.add(where.from())
                params.add(where.to())
                "${where.first()} BETWEEN ? AND ? "
            }
            is Like -> {
                params.add(where.last())
                "${where.first()} LIKE ? "
            }
            is In -> {
                params.add(where.others().joinToString{ it.toSqlString() })
                "${where.first()} IN (?) "
            }
            else -> throw Exception("Not Supported!قوللىمايدۇ")
        }
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
                buffer.append(expression.Select.top.run { if (this == null) "" else "LIMIT $this" })
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
        }
        val query = buffer.toString()
        var fullQuery = query
        params.forEach {
            fullQuery = fullQuery.replaceFirst("?", if (it.toString()[0] != '\'') it.toSqlString() else it.toString())
        }
        val retv = TranslateResult(encryptWithMD5(query), query, params.toList(), fullQuery, expression)
        params.clear()
        return retv
    }
}