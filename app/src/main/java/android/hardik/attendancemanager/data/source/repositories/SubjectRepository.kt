package android.hardik.attendancemanager.data.source.repositories

import android.hardik.attendancemanager.data.daos.SubjectDao
import android.hardik.attendancemanager.data.models.Subject
import androidx.lifecycle.LiveData

class SubjectRepository private constructor(
    private val subjectDao: SubjectDao
) {
    val allSubjects: LiveData<List<Subject>> = subjectDao.getAllSubjects()

    val allSubjectsTitles: LiveData<List<String>> = subjectDao.getAllSubjectsTitles()

    fun getSubject(title: String): LiveData<Subject?> {
        return subjectDao.getSubject(title)
    }

    suspend fun updateTitle(oldTitle: String?, newTitle: String?) {
        subjectDao.updateTitle(oldTitle!!, newTitle!!)
    }

    suspend fun insert(subject: Subject?) {
        subjectDao.insert(subject!!)
    }

    suspend fun update(subject: Subject?) {
        subjectDao.update(subject!!)
    }

    suspend fun incrementAttendedClasses(title: String) {
        subjectDao.incrementAttendedClasses(title)
    }

    suspend fun incrementMissedClasses(title: String) {
        subjectDao.incrementMissedClasses(title)
    }

    suspend fun delete(subject: Subject?) {
        subjectDao.delete(subject!!)
    }

    suspend fun delete(subjectTitle: String) {
        subjectDao.delete(subjectTitle)
    }

    suspend fun updateAttendance(title: String, attendedClasses: Int, missedClasses: Int) {
        subjectDao.updateAttendance(title, attendedClasses, missedClasses)
    }

    suspend fun resetAttendance(title: String) {
        updateAttendance(title, 0, 0)
    }

    companion object {

        @Volatile private var instance: SubjectRepository? = null

        fun getInstance(subjectDao: SubjectDao) =
                instance ?: synchronized(this) {
                    instance ?: SubjectRepository(subjectDao).also { instance = it }
                }
    }
}