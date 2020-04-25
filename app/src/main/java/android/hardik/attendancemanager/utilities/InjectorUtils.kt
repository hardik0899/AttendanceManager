package android.hardik.attendancemanager.utilities

import android.content.Context
import android.hardik.attendancemanager.data.database.MainDatabase
import android.hardik.attendancemanager.activities.main.viewmodels.SubjectDetailViewModelFactory
import android.hardik.attendancemanager.data.source.repositories.PeriodRepository
import android.hardik.attendancemanager.data.source.repositories.SubjectRepository
import android.hardik.attendancemanager.activities.timetable.viewmodels.PeriodListViewModelFactory
import android.hardik.attendancemanager.activities.main.viewmodels.SubjectViewModelFactory
import androidx.fragment.app.FragmentActivity

object InjectorUtils {
    private fun getSubjectRepository(context: Context): SubjectRepository {
        return SubjectRepository.getInstance(
                MainDatabase.getDatabase(context.applicationContext).subjectDao()
        )
    }

    private fun getPeriodRepository(context: Context): PeriodRepository {
        return PeriodRepository.getInstance(
                MainDatabase.getDatabase(context.applicationContext).periodDao(),
                MainDatabase.getDatabase(context.applicationContext).subjectPeriodDao()
        )
    }

    fun provideSubjectListViewModelFactory(activity: FragmentActivity): SubjectViewModelFactory {
        val repository = getSubjectRepository(activity)
        return SubjectViewModelFactory(repository)
    }

    fun provideSubjectDetailViewModelFactory(subjectTitle: String, activity: FragmentActivity): SubjectDetailViewModelFactory {
        val repository = getSubjectRepository(activity)
        return SubjectDetailViewModelFactory(subjectTitle, repository)
    }

    fun providePeriodListViewModelFactory(activity: FragmentActivity): PeriodListViewModelFactory {
        val repository = getPeriodRepository(activity)
        return PeriodListViewModelFactory(repository)
    }
}
