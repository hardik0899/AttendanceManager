package android.hardik.attendancemanager.data.daos

import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.data.models.SubjectWithPeriods
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SubjectPeriodDao {
    @get:Query("SELECT * FROM subject_table")
    @get:Transaction
    val subjectsWithPeriod: LiveData<List<SubjectWithPeriods>>

    @Transaction
    @Query("SELECT * FROM subject_table join period_table on title = subject_title where week_day=:weekDay")
    fun getAllSubjectsOn(weekDay: Int): LiveData<List<Subject>>
}