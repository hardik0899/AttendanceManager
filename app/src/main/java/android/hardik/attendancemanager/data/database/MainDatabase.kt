package android.hardik.attendancemanager.data.database

import android.content.Context
import android.hardik.attendancemanager.data.daos.PeriodDao
import android.hardik.attendancemanager.data.daos.SubjectDao
import android.hardik.attendancemanager.data.daos.SubjectPeriodDao
import android.hardik.attendancemanager.data.models.Period
import android.hardik.attendancemanager.data.models.Subject
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subject::class, Period::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun periodDao(): PeriodDao
    abstract fun subjectPeriodDao(): SubjectPeriodDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                        MainDatabase::class.java, "main_database")
                        .build()
            }
        }
    }
}