package com.sensacare.veepoo.room

import kotlin.reflect.KClass

/**
 * Stub implementations of Room database annotations and classes.
 * These are temporary implementations to allow compilation until proper Room integration is working.
 */

// Annotation stubs
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Entity(
    val tableName: String = "",
    val indices: Array<Index> = [],
    val primaryKeys: Array<String> = [],
    val foreignKeys: Array<ForeignKey> = [],
    val ignoredColumns: Array<String> = []
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Dao

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Database(
    val entities: Array<KClass<*>> = [],
    val version: Int = 1,
    val exportSchema: Boolean = true,
    val views: Array<KClass<*>> = []
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PrimaryKey(val autoGenerate: Boolean = false)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Insert(val onConflict: Int = 1) // ABORT = 1

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(val value: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Update(val onConflict: Int = 1) // ABORT = 1

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Delete

// Supporting annotation classes
annotation class Index(
    val value: Array<String> = [],
    val name: String = "",
    val unique: Boolean = false
)

annotation class ForeignKey(
    val entity: KClass<*>,
    val parentColumns: Array<String> = [],
    val childColumns: Array<String> = [],
    val onDelete: Int = 0, // NO_ACTION = 0
    val onUpdate: Int = 0, // NO_ACTION = 0
    val deferred: Boolean = false
)

// Class stubs
abstract class RoomDatabase {
    companion object {
        @JvmStatic
        fun <T : RoomDatabase> databaseBuilder(
            context: android.content.Context,
            klass: Class<T>,
            name: String
        ): Builder<T> {
            return Builder()
        }
    }

    class Builder<T : RoomDatabase> {
        fun build(): T {
            @Suppress("UNCHECKED_CAST")
            return null as T
        }

        fun allowMainThreadQueries(): Builder<T> = this
        fun fallbackToDestructiveMigration(): Builder<T> = this
        fun addCallback(callback: Callback): Builder<T> = this
    }

    abstract class Callback {
        open fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {}
        open fun onOpen(db: androidx.sqlite.db.SupportSQLiteDatabase) {}
    }
}

// Room class for database creation
object Room {
    @JvmStatic
    fun <T : RoomDatabase> databaseBuilder(
        context: android.content.Context,
        klass: Class<T>,
        name: String
    ): RoomDatabase.Builder<T> {
        return RoomDatabase.Builder()
    }
}
