/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/22/17
Time: 10:08 AM
 */

package Cn.Sarkar.EntityDream.Pipeline.Core

class ChildPipeLineContainer {
    private val contents = HashMap<Any, Any>()
    private val keys = ArrayList<Any>()

    operator fun <TSubject, TFeature>get(key: ChildPipeLineKey<TSubject, TFeature>): PipeLine<TSubject, TFeature>
    {
        return contents[key] as PipeLine<TSubject, TFeature>? ?: throw Exception("Not Found")
    }
    fun <TSubject, TFeature>add(key: ChildPipeLineKey<TSubject, TFeature>, pipeLine: PipeLine<TSubject, TFeature>){
        keys.add(key)
        contents.put(key, pipeLine)
    }

    fun <TSubject, TFeature>remove(key: ChildPipeLineKey<TSubject, TFeature>): PipeLine<TSubject, TFeature>
    {
        keys.remove(key)
        return contents.remove(key) as PipeLine<TSubject, TFeature>? ?: throw Exception("Not Found")
    }

    fun getAllKeys() = keys.map { it  as ChildPipeLineKey<Any, Any> }.toTypedArray()

    fun getAll() = contents.map { it as PipeLine<Any, Any> }.toTypedArray()
}