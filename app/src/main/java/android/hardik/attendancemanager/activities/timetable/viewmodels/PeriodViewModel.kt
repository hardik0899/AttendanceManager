package android.hardik.attendancemanager.activities.timetable.viewmodels

import android.hardik.attendancemanager.data.models.Period

import androidx.databinding.BaseObservable

class PeriodViewModel(private var period: Period? = null) : BaseObservable() {

    fun setPeriod(period: Period?) {
        this.period = period
    }

    val title: String?
        get() = period!!.subjectTitle

    val periodNumber: String
        get() = period!!.periodNumber.toString()
}
