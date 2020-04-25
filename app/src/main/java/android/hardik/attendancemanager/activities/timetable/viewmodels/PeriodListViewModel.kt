package android.hardik.attendancemanager.activities.timetable.viewmodels

import android.hardik.attendancemanager.data.models.Period
import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.data.source.repositories.PeriodRepository
import androidx.lifecycle.*

import kotlinx.coroutines.launch

import kotlin.collections.List

class PeriodListViewModel internal constructor(
        private val periodRepository: PeriodRepository
) : ViewModel() {
    private val allPeriods: LiveData<List<Period>> = periodRepository.allPeriods

    fun insert(period: Period) = viewModelScope.launch {
        periodRepository.insert(period)
    }

    fun update(period: Period) = viewModelScope.launch {
        periodRepository.update(period)
    }

    fun getAllPeriods(): LiveData<List<Period>> {
        return allPeriods
    }

    fun getAllPeriodsOn(weekDay: Int): LiveData<List<Period>> {
        return periodRepository.getAllPeriodsOn(weekDay)
    }

    fun getAllSubjectsOn(weekDay: Int): LiveData<List<Subject>> {
        return periodRepository.getAllSubjectsOn(weekDay)
    }

    fun deletePeriod(periodNumber: Int, weekDay: Int) = viewModelScope.launch {
        periodRepository.deletePeriod(periodNumber, weekDay)
    }
}
