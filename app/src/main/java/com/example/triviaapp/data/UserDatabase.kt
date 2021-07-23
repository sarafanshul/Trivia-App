package com.example.triviaapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.triviaapp.constant.DatabaseConstants
import com.example.triviaapp.data.entity.QnA
import com.example.triviaapp.data.entity.User
import java.util.concurrent.Executors

@Database(
    entities = [
        User::class ,
        QnA::class ,
    ] ,
    version = 3,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object{

        @Volatile
        private var INSTANCE : UserDatabase ?= null

        fun getInstance(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if( tempInstance != null )
                return tempInstance

            synchronized(this){
                val instance = buildDatabase(context)
                INSTANCE = instance
                return instance
            }
        }
        private fun buildDatabase( context: Context ) : UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext ,
                UserDatabase::class.java ,
                DatabaseConstants.DATABASE_NAME
            ).addCallback(object :  RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase){
                    super.onCreate(db)
                    // pre-populate data
                    // prepopulate works on a new thread so can return Null if database is not build while required
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {
                            /**
                             * TODO( Pre-Populate database if needed!)
                             */
                        }
                    }
                }
            }
            ).fallbackToDestructiveMigration() .build()
        }
    }
}