/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/10/18
Time: 11:21 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import org.junit.Test
import java.sql.DriverManager

internal class CreateTableTest {
    val connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308", "yeganaaa", "Developer653125")

    @Test
    fun createDb()
    {
        val statement = connection.prepareStatement("create database CreateNew;")
        val result = statement.executeUpdate()
        println(result)
    }
}