/**
Company: Sarkar software technologys
WebSite: http://www.sarkar.cn
Author: yeganaaa
Date : 12/4/17
Time: 12:25 PM
 */

package Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.Extensions

import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.*
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.KeyValuePair
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShip.RelationShipManyFieldConnector
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShip.RelationShipOneFieldConnector
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBuilderExtensions.SelectQueryExpression.fullColumnName
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.And
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.Equal
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.Common.WhereItemCondition
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.MappedParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryExpressionBlocks.SqlParameter
import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.IDataContext

abstract class DBEntity(override var DataContext: IDataContext, override var Table: IDBTable) : IDBEntity {
    override var values: ValuesCacheItem? = null
    override var FromDB: Boolean = false
}

/**
 * ھەر بىر Entity نىڭ بىردىنبىر بولغان پەرىقلەندۈرۈش كودى
 * 每一个Entity的唯一的标示符
 * Entity Unique key
 */
internal val IDBEntity.uniqueKey: String get() = "${this.Table.TableName}::${this.Table.PrimaryKey.columns.joinToString { "${it.ColumnName}::${values!![it].toString()}" }}"
/**
 * نۆۋەتتىكى Entity نىڭ PrimaryKey لىرىنىڭ ئىسمى ۋە قىممەتلىرى
 */
internal val IDBEntity.PrimaryKeyValues: Array<KeyValuePair<String, SqlParameter>> get() = arrayOf(*(this.Table.PrimaryKey.columns.map { KeyValuePair(it.fullColumnName, MappedParameter(values!![it]!!, it.DataType) as SqlParameter) }.toTypedArray()))

/**
 * قىممىتى ئاپتوماتىك ئاينىيدىغان ئىستون
 */
internal val IDBTable.autoIncrementColumn: IDBColumn<*>? get() =  PrimaryKey.columns.singleOrNull { it.AutoIncrement.autoIncrement }
/**
 *خاسلىق
 */
internal val IDBEntity.autoIncrementField: IDBColumn<*>? get() =  Table.autoIncrementColumn

/**
 * نۆۋەتتىكى Entity نىڭ مۇشۇ Entity  نى ئىزدەپ تىپىشقا بولىدىغان شەرتى،
 * ئادەتتە ئەڭ ئاسان ھەم ئاددىي ئۇسۇلدا ئەڭ ئاساسلىق مۇشۇ Entity نى تاپىدىغان شەرىتكە ئىرىشىشكە بولىدۇ
 */

internal val IDBEntity.simpleWhereCondition: WhereItemCondition get(){
    val conditions = ArrayList<WhereItemCondition>()
    this.PrimaryKeyValues.forEach {
        conditions.add(Equal({it.key}, {it.value}))
    }


    if (conditions.isEmpty()) throw Exception("چوقۇم Entity دا ئەڭ ئاز بولغاندا بىر دانە ئاچقۇچلۇق ئىستون بولىشى كىرەك")
    if (conditions.size == 1) return conditions.single()
    return And(*conditions.toTypedArray())
}

internal fun IDBEntity.generateRandomUnique() {
    val key = this.Table.PrimaryKey!!.columns.single { it.AutoIncrement.autoIncrement } as IDBColumn<Number>
    if (values!![key] == null) values!!.put(key.ColumnName, -1)

    while (DataContext.insertedIntities[uniqueKey] != null) {
        values!![key] = (values!![key]!! as Int) - 1
    }
}

fun <THISENTITY: IDBEntity, DESTINATIONENTITY: IDBEntity>THISENTITY.hasOne(relationShipColumn: IDBColumn<*>, entityGenerator: (context: IDataContext) -> DESTINATIONENTITY) : RelationShipOneFieldConnector<THISENTITY, DESTINATIONENTITY> = RelationShipOneFieldConnector(relationShipColumn, entityGenerator)
fun <THISENTITY: IDBEntity, DESTINATIONENTITY: IDBEntity>THISENTITY.hasMany(relationShipColumn: IDBColumn<*>, entityGenerator: (context: IDataContext) -> DESTINATIONENTITY) = RelationShipManyFieldConnector<THISENTITY, DESTINATIONENTITY>(relationShipColumn, entityGenerator)