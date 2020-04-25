package android.hardik.attendancemanager.activities.main.viewmodels

import android.hardik.attendancemanager.data.source.repositories.SubjectRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubjectDetailViewModelFactory(
        private val subjectTitle: String,
        private val repository: SubjectRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectDetailViewModel::class.java)) {
            return SubjectDetailViewModel(subjectTitle, repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}