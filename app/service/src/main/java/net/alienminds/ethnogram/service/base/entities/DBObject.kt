package net.alienminds.ethnogram.service.base.entities

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.time.Instant

abstract class DBObject internal constructor(
    private val querySnapshot: QueryDocumentSnapshot
){
    internal val documentId
        get() = querySnapshot.id
//
//    internal val isCache
//        get() = querySnapshot.metadata.isFromCache

    protected val map: Map<String, Any>
        get() = querySnapshot.data


//    protected inline fun <reified T>getValue(field: FieldNullable<T>): T?{
//        return map.withDefault { field.defaultValue() }
//            .let { it[field.key] as? T? }
//            ?: field.defaultValue()
//    }

    protected inline fun <reified T>getValue(field: Field<T>): T{
        return map.withDefault { field.defaultValue() }
            .let { it[field.key] as? T? }
            ?: field.defaultValue()
    }

    protected fun getValue(
        field: Field<Instant?>
    ): Instant? = querySnapshot
        .getTimestamp(field.key)
        ?.toInstant()
        ?: field.defaultValue()


//    data class FieldNullable<T> internal constructor(
//        val key: String,
//        val defaultValue: () -> T?
//    ){
//        internal constructor(
//            key: String,
//            default: T? = null
//        ): this(
//            key = key,
//            defaultValue = { default }
//        )
//    }

    data class Field<T> internal constructor(
        val key: String,
        val defaultValue: () -> T
    ){
        internal constructor(
            key: String,
            default: T
        ): this(
            key = key,
            defaultValue = { default }
        )
    }



}

data class InputField<out T>(
    val field: DBObject.Field<out T?>,
    val value: T
)