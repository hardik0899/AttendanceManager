package android.hardik.attendancemanager.data.daos

import android.hardik.attendancemanager.data.models.Subject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import kotlin.collections.List

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("DELETE FROM subject_table WHERE title = :title")
    suspend fun delete(title: String)

    @Query("SELECT * FROM subject_table ORDER BY title")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("UPDATE subject_table SET title = :newTitle WHERE title = :oldTitle")
    suspend fun updateTitle(oldTitle: String, newTitle: String)

    @Query("SELECT * FROM subject_table WHERE title = :title")
    fun getSubject(title: String): LiveData<Subject?>

    @Query("SELECT title FROM subject_table ORDER BY title")
    fun getAllSubjectsTitles(): LiveData<List<String>>

    @Query("UPDATE subject_table SET classes_attended = :attendedClasses, classes_missed = :missedClasses WHERE title = :title")
    suspend fun updateAttendance(title: String, attendedClasses: Int, missedClasses: Int)

    @Query("UPDATE subject_table SET classes_attended = classes_attended + 1 WHERE title = :title")
    suspend fun incrementAttendedClasses(title: String)

    @Query("UPDATE subject_table SET classes_missed = classes_missed + 1 WHERE title = :title")
    suspend fun incrementMissedClasses(title: String)

}
