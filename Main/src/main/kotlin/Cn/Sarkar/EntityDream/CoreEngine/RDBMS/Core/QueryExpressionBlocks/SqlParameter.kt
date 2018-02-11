/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 1/16/18
Time: 4:27 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDBDataType
import java.io.Serializable

interface SqlParameter{
    var Value: Any
}

/**
 * نورمال پارامىتىر
 */
data class NormalParameter(override var Value: Any) : SqlParameter

/**
 * بۇ پارامىتىرنىڭ سانلىق مەلۇمات تىپى قايسى؟
 * ئىشلەتكۈچى ئۆزى قايتىدىن بەلگىلىۋالغان سانلىق مەلۇمات تىپى ئىشلىتىلگەن پارامىتىر
 * مەسىلەن ئۆزىمىز يىڭىدىن ساندان سانلىق مەلۇمات تىپى بەلگىلىۋالغان بولساق شۇ سانلىق مەلۇمات تىپى مۇشۇ تۈر ئارقىلىق يەتكۈزىلىدۇ ۋە DataReader, DataWriter دىكى مۇناسىپ كودلار ئارقىلىق ئۆزىمىز بەلگىلىۋالغان
 * سانلىق مەلۇمات تىپى بىلەن سانداننىڭ سانلىق مەلۇمات تىپىنىڭ سىغىشىشچانلىقى ۋە بىر بىرىگە ئايلىنىشچانلىقى ئەمەلگە ئاشۇرىلىدۇ
 */
data class MappedParameter(override var Value: Any, var DataType: IDBDataType<*>) : SqlParameter
