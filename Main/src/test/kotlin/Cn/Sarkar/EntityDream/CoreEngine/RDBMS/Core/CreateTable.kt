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
    fun createDb() {

        class Person(var Name: String = "", var Age: Byte = -1) : Serializable


        val p1 = Person("yeganaaa", 23)
        val p2 = p1

        println(p1.Name)
        p2.Name = "Hello"
        println(p1.Name)

    }
}

