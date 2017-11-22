/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.Pipeline.Core

import Cn.Sarkar.EntityDream.Pipeline.Core.Info.PipeLineInfo

abstract class PipeLine<TSubject, TFeatureProvider>(vararg phases: Phase) {
    private val features: ArrayList<PipeLineFeatureContainer<TSubject, TFeatureProvider>> = ArrayList()
    internal val cachedFeatures: ArrayList<PipeLineFeature<TSubject, TFeatureProvider>> = ArrayList()

    abstract val info: PipeLineInfo

    init {
        phases.forEach(this::addPhase)
    }

    fun execute(featureProvider: TFeatureProvider, subject: TSubject) : TSubject
    {
         return PipeLineContext(this, featureProvider).proceedWith(subject)
    }

    fun isExistsThisPhase(phase: Phase): Boolean
    {
        return features.filter { innerItem -> innerItem.key.name == phase.name }.isNotEmpty()
    }

    fun addPhase(phase: Phase) {
        if (isExistsThisPhase(phase)) throw Exception("This phase: ${phase} is allready exists.")
        features.add(PipeLineFeatureContainer(phase))
    }

    fun removePhase(phase: Phase) {
        val item = features.singleOrNull { innerItem -> innerItem.key.name == phase.name }
        if (item == null) throw Exception("This phase: ${phase} is not exists.")
        features.add(item)
    }

    fun addContainer(container: PipeLineFeatureContainer<TSubject, TFeatureProvider>)
    {
        if (!isExistsThisPhase(container.key)) addPhase(container.key)
        this[container.key].merge(container)
    }

    fun removeContainer(container: PipeLineFeatureContainer<TSubject, TFeatureProvider>)
    {
        features.remove(this[container.key])
    }

    fun setPhaseEnable(phase: Phase, value: Boolean)
    {
        val itemPhase = features.singleOrNull { it.key == phase }
        if (itemPhase == null) throw Exception("This phase: ${phase} is not exists.")
        itemPhase.enable = value
        clearCache()
        cache()
    }

    fun clearCache(){
        cachedFeatures.clear()
    }

    fun cache()
    {
        features.filter { it.enable }.forEach {
            cachedFeatures.addAll(it.getAll())
        }
    }

    operator fun get(phase: Phase) : PipeLineFeatureContainer<TSubject, TFeatureProvider>
    {
        val itemPhase = features.singleOrNull { it.key.name == phase.name }
        if (itemPhase == null) throw Exception("This phase: ${phase} is not exists.")
        return itemPhase
    }

    fun getAllContainer() = features.toTypedArray()
}
