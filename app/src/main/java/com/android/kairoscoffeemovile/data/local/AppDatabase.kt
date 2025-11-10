package com.android.kairoscoffeemovile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.kairoscoffeemovile.data.local.dao.CartDao
import com.android.kairoscoffeemovile.data.local.dao.ProductDao
import com.android.kairoscoffeemovile.data.local.dao.UserDao
import com.android.kairoscoffeemovile.data.local.entities.CartItem
import com.android.kairoscoffeemovile.data.local.entities.Product
import com.android.kairoscoffeemovile.data.local.entities.User

@Database(
    entities = [Product::class, CartItem::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kairos_coffee_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}