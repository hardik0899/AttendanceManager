package android.hardik.attendancemanager.activities.timetable.viewmodels

import android.hardik.attendancemanager.data.source.repositories.PeriodRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PeriodListViewModelFactory(
        private val repository: PeriodRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeriodListViewModel::class.java)) {
            return PeriodListViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}