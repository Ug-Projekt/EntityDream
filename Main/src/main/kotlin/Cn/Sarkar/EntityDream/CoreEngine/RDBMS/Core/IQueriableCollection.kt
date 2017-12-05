/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/5/17
Time: 9:46 AM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

interface IQueriableCollection<ENTITY : IDBEntity> {
    var Context: IDataContext
    var ItemGenerator: () -> ENTITY
}