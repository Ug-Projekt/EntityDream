/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/17/17
Time: 5:21 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType

import java.io.Serializable

interface IDBDataType<KOTLINDATATYPE> : Serializable{
    var Name: String
    var DefaultValue: KOTLINDATATYPE
}