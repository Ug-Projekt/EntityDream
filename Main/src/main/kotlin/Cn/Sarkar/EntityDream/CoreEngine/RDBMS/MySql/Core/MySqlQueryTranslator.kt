/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/15/17
Time: 1:57 AM
 */

package Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.MySql.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IQueryTranslator
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Select.*
import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.QueryBlocks.Common.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Delete.DeleteQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Insert.InsertQueryExpression
import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.QueryBlocks.Select.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Update.UpdateQueryExpression
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.TranslateResult
import sun.misc.BASE64Encoder
import java.security.MessageDigest

class MySqlQueryTranslator : IQueryTranslator {
    val params = ArrayList<Any>()

    val md5Algorithm by lazy { MessageDigest.getInstance("MD5") }
    fun encryptWithMD5(sourceString: String): String {
        return BASE64Encoder().encode(md5Algorithm.digest(sourceString.toByteArray()))
    }

    //    fun anyObjectRenderToSqlString(any: Any) : String = when(any)
//    {
//        is String -> "'$any'"
//        is Int, is Float, is Double -> any.toString()
//        else -> any.toString()
//    }
    fun anyObjectRenderToSqlString(any: Any): String {
        params.add(any)
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

    fun recursiveWhereBlock(where: WhereItemCondition): String {
        return when (where) {
            is AlwaysSelect -> ""
            is And -> {
                "(${where.conditions.joinToString(separator = " AND ") { recursiveWhereBlock(it) }})"
            }
            is Or -> {
                "(${where.conditions.joinToString(separator = " Or ") { recursiveWhereBlock(it) }})"
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
                params.add(where.others().joinToString { it.toString() })
                "${where.first()} IN (?) "
            }
            else -> throw Exception("Not Supported!قوللىمايدۇ")
        }
    }

    override fun Translate(expression: IQueryExpression): TranslateResult {
        val buffer = StringBuffer()

        when (expression) {
            is SelectQueryExpression -> {

                buffer.append("SELECT ")
                buffer.append(expression.Select.selectors.joinToString { "${it.SourceName}${it.AliasName.run { if (this == null) "" else " AS ${this}" }}" })
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
                buffer.append(expression.Where.condition.run { if (this is AlwaysSelect) "" else "WHERE ${recursiveWhereBlock(expression.Where.condition)}" })
                buffer.append(expression.OrderBy.run { if (this == null) "" else "ORDER BY ${this.Name} ${this.orderMode.Name} " })
                buffer.append(expression.Select.top.run { if (this == null) "" else "LIMIT $this" })
                buffer.append(";")
//                println(buffer)
            }
            is UpdateQueryExpression -> {

                buffer.append("UPDATE ${expression.update.TableName} SET ${expression.set.Values.joinToString { "${it.key}=${it.value.run { anyObjectRenderToSqlString(this) }}" }} ")
                buffer.append("WHERE ${recursiveWhereBlock(expression.where.condition)}")
                buffer.append(";")
//                println(buffer)
            }
            is InsertQueryExpression -> {
                buffer.append("INSERT INTO ${expression.insertInto.TableName} (${expression.insertInto.Columns.joinToString { it }}) VALUES (${expression.values.Values.joinToString { anyObjectRenderToSqlString(it) }})")
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
        val retv = TranslateResult(encryptWithMD5(query), query, params.toList())
        params.clear()
        return retv
    }
}