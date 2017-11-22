/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/17/17
Time: 5:08 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector

interface IDBField<KOTLINDATATYPE> {
    var FieldName: String
    var Column: IDBColumn<KOTLINDATATYPE>
    var CachedValue: KOTLINDATATYPE
}