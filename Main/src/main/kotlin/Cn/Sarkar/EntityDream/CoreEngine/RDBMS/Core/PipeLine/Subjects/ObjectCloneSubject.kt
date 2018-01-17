/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/17/18
Time: 10:56 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject

class ObjectCloneSubject(val SourceObject: Any) : IPipeLineSubject {
    override val Name: String = "Object Clone"
    override val Description: String = "Clone Any Objects"
    var ClonedObject: Any? = null
}