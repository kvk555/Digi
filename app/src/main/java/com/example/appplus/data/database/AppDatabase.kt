package com.example.appplus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appplus.BuildConfig
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [ItemDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val passPhrase: ByteArray =
            SQLiteDatabase.getBytes(BuildConfig.DB_KEY.toCharArray())
        val factory = SupportFactory(passPhrase)

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "encrypted_db")
                .openHelperFactory(factory = factory)
                .build()
        }
    }

    abstract fun getAppDao(): AddDao

}
