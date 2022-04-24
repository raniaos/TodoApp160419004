package com.ubaya.todoapp160419004.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.todoapp160419004.util.MIGRATION_1_2
import com.ubaya.todoapp160419004.util.MIGRATION_2_3

@Database(entities = arrayOf(Todo::class), version =  3)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "newtododb").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
        operator fun invoke(context:Context) {
            if(instance!=null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}

//@Database(entities = arrayOf(Todo::class), version =  2)
//abstract class TodoDatabase:RoomDatabase() {
//    abstract fun todoDao(): TodoDao
//
//    companion object {
//        @Volatile private var instance: TodoDatabase ?= null
//        private val LOCK = Any()
//
//        private fun buildDatabase(context:Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                TodoDatabase::class.java,
//                "newtododb").addMigrations(MIGRATION_1_2).build()
//        operator fun invoke(context:Context) {
//            if(instance!=null) {
//                synchronized(LOCK) {
//                    instance ?: buildDatabase(context).also {
//                        instance = it
//                    }
//                }
//            }
//        }
//    }
//}

//@Database(entities = arrayOf(Todo::class), version =  1)
//abstract class TodoDatabase:RoomDatabase() {
//    abstract fun todoDao(): TodoDao
//
//    companion object {
//        @Volatile private var instance: TodoDatabase ?= null
//        private val LOCK = Any()
//
//        private fun buildDatabase(context:Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                TodoDatabase::class.java,
//                "newtododb").build()
//        operator fun invoke(context:Context) {
//            if(instance!=null) {
//                synchronized(LOCK) {
//                    instance ?: buildDatabase(context).also {
//                        instance = it
//                    }
//                }
//            }
//        }
//    }
//}
