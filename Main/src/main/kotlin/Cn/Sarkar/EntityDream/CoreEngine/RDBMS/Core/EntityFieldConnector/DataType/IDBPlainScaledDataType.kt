/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/17/17
Time: 5:55 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType

interface IDBPlainScaledDataType<KOTLINDATATYPE>  : IDataType<KOTLINDATATYPE> {
    var ScaleValue: Int
}