import Cn.Sarkar.EntityDream.Pipeline.Core.*
import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo


val begin = Phase("Begin")
val process= Phase("Process")
val end = Phase("End")

class TestClass<T, E>(val t: T, val e: E)

val list = ArrayList<Any>()

fun main(args: Array<String>) {
    list.add(TestClass("Hello", 23))
    list.add(TestClass(23, "Hello"))
    val result = list[0] as TestClass<Any, Any>
    val resultTwo = result as TestClass<String, Int>
    println(resultTwo.e)
    println(resultTwo.t)
}

data class Student(var name: String, var age: Byte, var Score: Int)

class Writer{
    fun write(any: Any)
    {
        println(any)
    }
}

class Translator: PipeLineFeature<Student, Writer>(){
    companion object {
        val beginActionKey = ChildPipeLineKey<String, Student>("brginActionKey")
    }

    init {
//        childPipeLines.add(beginActionKey, null!!)
    }

    override val getMetaData: PipeLineFeatureMetaData = PipeLineFeatureMetaData(process, "Translator")
    override val info: FeatureInfo
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var enable: Boolean = true

    override fun PipeLineContext<Student, Writer>.onExecute(subject: Student) {

    }
}
