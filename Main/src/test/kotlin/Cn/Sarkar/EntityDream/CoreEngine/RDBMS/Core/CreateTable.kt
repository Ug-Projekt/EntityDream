/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/10/18
Time: 11:21 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util.clone
import org.junit.Test
import org.omg.CORBA.PERSIST_STORE
import java.io.Serializable
import java.sql.DriverManager

internal class CreateTableTest {

    @Test
    fun test() {
        val connection = DriverManager.getConnection("jdbc:sqlite:/media/yeganaaa/aeb9a6f0-dc79-405d-a154-3355e7a240c3/Temp/data.db")
        val statement = connection.prepareStatement("SELECT Company.ID AS ID, Company.Name AS Name, Company.WebSite AS WebSite FROM Company WHERE Company.WebSite = ? LIMIT 1;")
        statement.setString(1, "http://www.sarkar.cn")
        statement.addBatch()

        val result = statement.executeQuery()

        while (result.next()) {
            println(result.getString("Name"))
        }
    }
}

