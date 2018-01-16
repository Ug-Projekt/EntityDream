/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/5/17
Time: 8:21 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Util

import java.io.*

fun <T : Serializable> T.serializeToByteArray() : ByteArray{
    val byteOutStream = ByteArrayOutputStream()
    val objectOutStream = ObjectOutputStream(byteOutStream)
    println(this)
    objectOutStream.writeObject(this)
    objectOutStream.flush()
    objectOutStream.close()
    val clonedObjectBytes = byteOutStream.toByteArray()
    byteOutStream.close()
    return clonedObjectBytes
}

fun <T : Serializable> ByteArray.deserializeFromByteArray() : T
{
    val byteInStream = ByteArrayInputStream(this)
    val objectInStream = ObjectInputStream(byteInStream)
    val clonedObject = objectInStream.readObject() as T
    objectInStream.close()
    byteInStream.close()
    return clonedObject
}

fun <T : Serializable> T.clone() : T = this.serializeToByteArray().deserializeFromByteArray()

