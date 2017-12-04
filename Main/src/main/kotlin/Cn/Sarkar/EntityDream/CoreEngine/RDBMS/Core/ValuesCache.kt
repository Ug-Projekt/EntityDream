/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 11/22/17
Time: 9:16 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

class ValuesCacheItem : LinkedHashMap<String, Any?>()
{
    operator fun <T> get(column: IDBColumn<T>) : T? = get(column.ColumnName) as? T
    operator fun <T> set(column: IDBColumn<T>, value: T){
        set(column.ColumnName, value)
    }
}

class ValuesCache : ArrayList<ValuesCacheItem>(){

}
