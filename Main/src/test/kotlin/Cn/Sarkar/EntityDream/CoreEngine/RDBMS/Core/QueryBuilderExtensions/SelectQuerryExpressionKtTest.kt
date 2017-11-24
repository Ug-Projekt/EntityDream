package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.IDBColumn
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBTable
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.ValuesCache
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext
import org.junit.Assert
import org.junit.Test

internal class SelectQuerryExpressionKtTest {

    class PersonTable : IDBTable{
        override var TableName: String = "USers"
            set(value) {}
        override var PrimaryKey: IDBColumn<*>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var Columns: Array<IDBColumn<*>>
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}

    }

    object Person
    {
        val Name = object : IDBColumn<String>{
            override var Table: IDBTable = PersonTable()
            override var NotNull: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var DataType: IDataType<String>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var AutoIncrement: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Unique: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ForeignKey: IDBColumn<*>?
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Index: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ColumnName: String = "Name"

        }

        val Age = object : IDBColumn<Int>{
            override var Table: IDBTable
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var NotNull: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var DataType: IDataType<Int>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var AutoIncrement: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var Unique: Boolean
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
                set(value) {}
            override var ForeignKey: IDBColumn<*>?
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
        override var fieldValues: ValuesCache
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var DataContext: IDataContext
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
        override var DBTable: IDBTable = PersonTable()

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

        Assert.assertEquals(conditions[0].first(), Person.Name.ColumnName)
        Assert.assertEquals(conditions[0].last(), name)

        Assert.assertEquals(conditions[1].first(), Person.Age.ColumnName)
        Assert.assertEquals(conditions[1].last(), age)

        Assert.assertEquals(conditions[2].first(), Person.Name.ColumnName)
        Assert.assertEquals(conditions[2].last(), "Hello")
    }

    object InputMode
    {
        val Text = 1
        val Number = 2
        val PhoneNumber = 4
        val EMail = 8
        val WebAddr = 16
        val Decimal = 32
    }

    infix fun Int.isIn(destination: Int) : Boolean = (this and destination) > 0

    @Test
    fun go(){
        val result = InputMode.run { PhoneNumber or EMail or Decimal }

        val numbers = InputMode.run {
            arrayOf(Text, Number, PhoneNumber, EMail, WebAddr, Decimal)
        }

        numbers.forEach {
            println(it isIn result)
        }
    }

    @Test
    fun where(){
//        val collection: DBCollection<PersonEntity>? = null
//
//        val result = collection!! where {
//            Person.Name.like("%yeganaaa%").and(Person.Age.greater(18)).or(Person.Age.between(0.to(100))).or(Person.Name.iN("yeganaaa", "hello", "Go"))
//            Person.Name like "%yeganaaa%" and (Person.Age greater 18) or (Person.Age between (0 to 100)) or (Person.Name.iN("yeganaaa", "hello", "Go"))
//        }
//
//        val result2 = collection!! where {Person.Name equals "yeganaaa" and (Person.Age greater 20)}
    }

}


