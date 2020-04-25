package android.hardik.attendancemanager.activities.main.viewmodels

import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.data.source.repositories.SubjectRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SubjectDetailViewModel internal constructor(
        val subjectTitle: String,
        private val subjectRepository: SubjectRepository
) : ViewModel() {
    private val subject = subjectRepository.getSubject(subjectTitle)

    val title: LiveData<String>
        get() = Transformations.map(subject) {
            it?.title
        }

    val attendedClasses: LiveData<String>
        get() = Transformations.map(subject) {
            it?.attendedClasses.toString()
        }

    val totalClasses: LiveData<String>
        get() = Transformations.map(subject) {
            it?.totalClasses.toString()
        }

    val subjectPercentage: LiveData<String>
        get() = Transformations.map(subject) {
            getFormattedPercentageString(it!!)
        }

    private fun getFormattedPercentageString(subject: Subject): String {
        if (subject.totalClasses == 0) {
            return "0.0%"
        }
        val percentage = (subject!!.attendedClasses.toDouble() / subject!!.totalClasses) * 100
        return String.format(java.util.Locale.ENGLISH, "%.1f%%", percentage)
    }
}
