/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.Pipeline.Core

class PipeLineFeatureContainer<TSubject, TFeatureProvider>(val key: Phase)  {
    private val features: ArrayList<PipeLineFeature<TSubject, TFeatureProvider>> = ArrayList()
    var enable = true

    operator fun get(key: PipeLineFeatureMetaData) : PipeLineFeature<TSubject, TFeatureProvider>
    {
        val interceptor = features.singleOrNull { it.getMetaData.addr == key.addr }
        if (interceptor == null) throw Exception("Not Found")
        return interceptor
    }

    fun remove(key: PipeLineFeatureMetaData, ifIsExists: Boolean) {
        val interceptor = features.singleOrNull { it.getMetaData.addr == key.addr }
        if (interceptor == null) {
            if (!ifIsExists) throw Exception("Not Found")
        }
        else {
            features.remove(interceptor)
        }
    }

    fun add(feature: PipeLineFeature<TSubject, TFeatureProvider>){
        if (feature.getMetaData.phase.name != this.key.name) throw Exception("this feature phase and container key phase is not match.")
        if (features.filter { it.getMetaData.addr == feature.getMetaData.addr}.isNotEmpty()) throw Exception("this feature is allready exists")
        features.add(feature)

    }

    fun merge(container: PipeLineFeatureContainer<TSubject, TFeatureProvider>){
        container.getAll().forEach(this::add)
    }

    fun getAll() = features.toTypedArray()
}