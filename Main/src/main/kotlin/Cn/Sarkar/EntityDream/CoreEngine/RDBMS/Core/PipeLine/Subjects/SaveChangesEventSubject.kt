/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 3/3/18
Time: 1:02 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.Subjects

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.PipeLine.IPipeLineSubject
enum class SaveChangesEventState{
    Begin, Over
}
class SaveChangesEventSubject(var State: SaveChangesEventState) : IPipeLineSubject {
    override val Name: String by lazy { "Save changes event" }
    override val Description: String by lazy { "emit Save changes event" }
}