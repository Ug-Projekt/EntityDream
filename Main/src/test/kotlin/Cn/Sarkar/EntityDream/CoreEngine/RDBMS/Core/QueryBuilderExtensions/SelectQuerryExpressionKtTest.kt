package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions

//internal class SelectQuerryExpressionKtTest {
//
//    val translator = MySqlQueryTranslator()
//
//    object PersonTable : IDBTable{
//        override var TableName: String = "Users"
//            set(value) {}
//        override var PrimaryKey: Array<IDBColumn<*>>
//            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//            set(value) {}
//        override var Columns: Array<IDBColumn<*>>
//            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//            set(value) {}
//
//    }
//
//    object Person
//    {
//        val Name = object : IDBColumn<String> {
//            override fun getParameter(thisRef: IDBEntity, property: KProperty<*>): String {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun setValue(thisRef: IDBEntity, property: KProperty<*>, value: String) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override var Table: IDBTable = PersonTable
//            override var NotNull: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var DataType: IDBDataType<String>
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var AutoIncrement: AutoIncrementProperty
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var Unique: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var ForeignKey: ForeignKey?
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var Index: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var ColumnName: String = "Name"
//
//        }
//
//        val Age = object : IDBColumn<Int> {
//            override fun getParameter(thisRef: IDBEntity, property: KProperty<*>): Int {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun setValue(thisRef: IDBEntity, property: KProperty<*>, value: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override var Table: IDBTable
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var NotNull: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var DataType: IDBDataType<Int>
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var AutoIncrement: AutoIncrementProperty
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var Unique: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var ForeignKey: ForeignKey?
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var Index: Boolean
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//                set(value) {}
//            override var ColumnName: String = "Age"
//
//        }
//    }
//
//    class PersonEntity : IDBEntity
//    {
//        override var values: ValuesCacheItem?
//            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//            set(value) {}
//        override var DataContext: IDataContext
//            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//            set(value) {}
//        override var Table: IDBTable = PersonTable
//
//    }
//
//    @Test
//    fun equals() {
//        val name = "yeganaaa"
//        val age = 23
//
//        val result1 = Person.Name equals name
//        val result2 = Person.Age equals age
//
//        Assert.assertEquals(result1.first(), Person.Name.ColumnName)
//        Assert.assertEquals(result1.last(), name)
//
//        Assert.assertEquals(result2.first(), Person.Age.ColumnName)
//        Assert.assertEquals(result2.last(), age)
//    }
//
//    @Test
//    fun and() {
//        val name = "yeganaaa"
//        val age = 23
//
//        val result1 = (Person.Name equals name) and (Person.Age equals age) and (Person.Name equals "Hello")
//        val conditions = result1.conditions.map { it as Equal }
//
//        Assert.assertEquals(conditions[0].first(), Person.Name.fullColumnName)
//        Assert.assertEquals(conditions[0].last(), name)
//
//        Assert.assertEquals(conditions[1].first(), Person.Age.fullColumnName)
//        Assert.assertEquals(conditions[1].last(), age)
//
//        Assert.assertEquals(conditions[2].first(), Person.Name.fullColumnName)
//        Assert.assertEquals(conditions[2].last(), "Hello")
//    }


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

//    @Test
//    fun where(){
//
//        val expr = PersonTable.SelectQuery<PersonEntity>()
//        val result = translator.Translate(expr)
//
//        println(result.fullSqlQuery)
//    }

//}


