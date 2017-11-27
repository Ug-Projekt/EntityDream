/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 4:33 PM
 */

package Cn.Sarkar.EntityDream.Pipeline.Extension

import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLineFeature
import Cn.Sarkar.EntityDream.Pipeline.Core.PipeLine

fun <TSubject, TFeatureProvider> PipeLine<TSubject, TFeatureProvider>.installFeature(feature: PipeLineFeature<TSubject, TFeatureProvider, Any?>){
    this[feature.getMetaData.phase].add(feature)
    clearCache()
    cache()
}
fun <TSubject, TFeatureProvider> PipeLine<TSubject, TFeatureProvider>.tryInstallFeature(feature: PipeLineFeature<TSubject, TFeatureProvider, Any?>) = try {
        installFeature(feature)
        true
    }
    catch (excep: Exception) {
        false
    }