/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 3:06 PM
 */

package Cn.Sarkar.EntityDream.Pipeline.Core

class PipeLineContext<TSubject, TFeatureProvider>(val pipeLine: PipeLine<TSubject, TFeatureProvider>, val featureContext: TFeatureProvider) {
    private var index = 0
    var subject: TSubject? = null
    private set

    fun proceedWith(subject: TSubject) : TSubject
    {
        this.subject = subject

        while (index < pipeLine.cachedFeatures.size) {
            index++
            this.pipeLine.cachedFeatures[index - 1].apply { if (this.enable) onExecute(this@PipeLineContext.subject!!) }
        }

        return this.subject!!
    }

    fun respond(subject: TSubject) : TSubject
    {
        finish()
        this.subject = this.pipeLine.execute(featureContext, subject)
        return this.subject!!
    }

    fun finish(){
        index = pipeLine.cachedFeatures.size
    }
}
