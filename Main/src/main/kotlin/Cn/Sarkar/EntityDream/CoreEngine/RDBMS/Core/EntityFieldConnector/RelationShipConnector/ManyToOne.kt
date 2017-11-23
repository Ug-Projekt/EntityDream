///**
//Company: Sarkar software technologys
//WebSite: http://www.sarkar.cn
//Author: yeganaaa
//Date : 11/18/17
//Time: 11:01 PM
// */
//
//package Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.RelationShipConnector
//
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.AbstractDBField
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.IDataType
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.EntityFieldConnector.DataType.ORMRelationShip
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.IDBEntity
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Equal
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.Core.QueryBlocks.Common.Where
//import Cn.SarkarMMS.DataAccessLayer.CoreEngine.RDBMS.Core.QueryBlocks.Select.*
//import Cn.Sarkar.EntityDream.CoreEngine.RDBMS.WriteParameters
//import java.sql.PreparedStatement
//import kotlin.reflect.KProperty
//
//class ManyToOne<PROPERTY : IDBEntity>(defaultValue: PROPERTY, var toDestTablePrimaryKey: String, var lookUpIfTheValue: () -> Any) : AbstractDBField<PROPERTY>() {
//    override var FieldName: String = "RelationShip Field, Unnecessary!"
//    override var NotNull: Boolean = false
//    override var AutoIncrement: Boolean = false
//    override var Unique: Boolean = false
//    override var PrimaryKey: Boolean = false
//    override var DataType: IDataType = ORMRelationShip()
//
//    override var Cache: PROPERTY = defaultValue
//
//    private var initialized = false
//
//    override fun setValue(entity: IDBEntity, property: KProperty<*>, value: PROPERTY) {
//
//    }
//
//    override fun getValue(entity: IDBEntity, property: KProperty<*>): PROPERTY {
//        if (!initialized) {
//            val expression = SelectQueryExpression(Select(FromColumn(toDestTablePrimaryKey)), From(FromTable(Cache.TableName)), Where(Equal({toDestTablePrimaryKey}, {lookUpIfTheValue()})))
//            val result = entity.DataContext.queryTranslator().Translate(expression)
//            val statement = entity.DataContext.connection.prepareStatement(result.sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)
//            result.parameters.forEachIndexed { index, any -> statement.WriteParameters(index + 1, any) }
//            val idResult = statement.executeQuery()
//            if (idResult.next())
//            {
//                Cache.ID = idResult.getInt(1)
//            }
//            idResult.close()
//            statement.close()
//            initialized = true
//        }
//
//        return Cache
//    }
//}