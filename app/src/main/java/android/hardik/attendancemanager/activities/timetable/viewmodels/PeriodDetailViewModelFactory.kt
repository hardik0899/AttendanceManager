package android.hardik.attendancemanager.activities.timetable.viewmodels

import android.app.Application
import android.hardik.attendancemanager.data.source.repositories.PeriodRepository
import androidx.lifecycle.ViewModelProvider

class PeriodDetailViewModelFactory(
        val periodId: Long,
        val periodRepository: PeriodRepository,
        val application: Application
) : ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return PeriodDetailViewModel(PeriodRepository(application), periodId) as T
//    }

    init {
        val period = periodRepository.getPeriod(periodId)
    }
}