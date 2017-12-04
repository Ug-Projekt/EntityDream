package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.AutoIncrementProperty
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ForeignKey
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.MySql.Core.MySqlQueryTranslator
import org.junit.Assert
import org.junit.Test

internal class SelectQuerryExpressionKtTest {

    val translator = MySqlQueryTranslator()

    object PersonTable : IDBTable{
        override var TableName: String = "Users"
            set(value) {}
        override var PrimaryKey: Array<IDBColumn<*>>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var Columns: Array<IDBColumn<*>>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}

    }

    object Person
    {
        val Name = object : IDBColumn<String> {
            override var Table: IDBTable = PersonTable
            override var NotNull: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var DataType: IDataType<String>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var AutoIncrement: AutoIncrementProperty
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Unique: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ForeignKey: ForeignKey?
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Index: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ColumnName: String = "Name"

        }

        val Age = object : IDBColumn<Int> {
            override var Table: IDBTable
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var NotNull: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var DataType: IDataType<Int>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var AutoIncrement: AutoIncrementProperty
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Unique: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ForeignKey: ForeignKey?
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Index: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ColumnName: String = "Age"

        }
    }

    class PersonEntity : IDBEntity
    {
        override var fieldValues: ValuesCacheItem
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var DataContext: IDataContext
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var DBTable: IDBTable = PersonTable

    }

    @Test
    fun equals() {
        val name = "yeganaaa"
        val age = 23

        val result1 = Person.Name equals name
        val result2 = Person.Age equals age

        Assert.assertEquals(result1.first(), Person.Name.ColumnName)
        Assert.assertEquals(result1.last(), name)

        Assert.assertEquals(result2.first(), Person.Age.ColumnName)
        Assert.assertEquals(result2.last(), age)
    }

    @Test
    fun and() {
        val name = "yeganaaa"
        val age = 23

        val result1 = (Person.Name equals name) and (Person.Age equals age) and (Person.Name equals "Hello")
        val conditions = result1.conditions.map { it as Equal }

        Assert.assertEquals(conditions[0].first(), Person.Name.fullColumnName)
        Assert.assertEquals(conditions[0].last(), name)

        Assert.assertEquals(conditions[1].first(), Person.Age.fullColumnName)
        Assert.assertEquals(conditions[1].last(), age)

        Assert.assertEquals(conditions[2].first(), Person.Name.fullColumnName)
        Assert.assertEquals(conditions[2].last(), "Hello")
    }


//
//    object InputMode
//    {
//        val Text = 1
//        val Number = 2
//        val PhoneNumber = 4
//        val EMail = 8
//        val WebAddr = 16
//        val Decimal = 32
//    }
//
//    infix fun Int.isIn(destination: Int) : Boolean = (this and destination) > 0
//
//    @Test
//    fun go(){
//        val result = InputMode.run { PhoneNumber or EMail or Decimal }
//
//        val numbers = InputMode.run {
//            arrayOf(Text, Number, PhoneNumber, EMail, WebAddr, Decimal)
//        }
//
//        numbers.forEach {
//            println(it isIn result)
//        }
//    }

    @Test
    fun where(){

        val expr = PersonTable.SelectQuery
        val result = translator.Translate(expr)

        println(result.fullSqlQuery)
    }

}


