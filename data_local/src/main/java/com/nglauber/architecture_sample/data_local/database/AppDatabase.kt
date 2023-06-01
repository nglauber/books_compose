package com.nglauber.architecture_sample.data_local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nglauber.architecture_sample.data_local.dao.BookDao
import com.nglauber.architecture_sample.data_local.entity.Book

@Database(
    entities = [Book::class],
    version = AppDatabase.DB_VERSION,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        private const val DB_NAME = "booksDb"
        internal const val DB_VERSION = 1

        private var instance: AppDatabase? = null

        fun getDatabase(context: Context, inMemory: Boolean = false): AppDatabase {
            if (instance == null) {
                instance = if (inMemory) {
                    Room.inMemoryDatabaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                    ).allowMainThreadQueries().build()
                } else {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }

            }
            return instance as AppDatabase
        }

        fun destroyInstance() {
            instance = null
        }
    }
}
