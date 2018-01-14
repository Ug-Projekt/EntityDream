/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 3:50 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType

interface IDBNumberType<KOTLINDATATYPE> : IDBDataType<KOTLINDATATYPE>
{
    var Unsigned: Boolean
}