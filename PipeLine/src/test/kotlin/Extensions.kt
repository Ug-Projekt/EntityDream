/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 10/17/17
Time: 6:05 PM
 */

fun getElapsedTimeMills(action :() -> Unit) : Long
{
    var ms = System.currentTimeMillis()
    action()
    ms = System.currentTimeMillis() - ms
    return ms
}