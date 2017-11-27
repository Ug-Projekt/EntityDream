/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.Pipeline.Core

import Cn.Sarkar.EntityDream.Pipeline.Core.Info.FeatureInfo

abstract class PipeLineFeature<TSubject, TFeatureProvider, T> {
    val childPipeLines = ChildPipeLineContainer()
    open var enable: Boolean = true

    abstract val getMetaData : PipeLineFeatureMetaData
    abstract val info: FeatureInfo
    abstract fun PipeLineContext<TSubject, TFeatureProvider>.onExecute(subject: TSubject)
}
