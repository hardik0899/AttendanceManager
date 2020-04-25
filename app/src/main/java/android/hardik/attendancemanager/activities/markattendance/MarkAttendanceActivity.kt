package android.hardik.attendancemanager.activities.markattendance

import android.content.Context
import android.content.Intent
import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.databinding.ActivityMarkAttendanceBinding
import android.hardik.attendancemanager.activities.timetable.fragments.PeriodFragment.Companion.newInstance
import android.hardik.attendancemanager.data.models.Period
import android.hardik.attendancemanager.data.models.Subject
import android.hardik.attendancemanager.activities.timetable.viewmodels.PeriodListViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import timber.log.Timber
import java.util.*
import android.hardik.attendancemanager.utilities.InjectorUtils

class MarkAttendanceActivity : AppCompatActivity() {

    private var weekDay = 0
    private lateinit var periodListViewModel: PeriodListViewModel
    private lateinit var periodViewPager: ViewPager2
    private lateinit var pagerAdapter: PeriodPagerAdapter
    private lateinit var binding: ActivityMarkAttendanceBinding
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mark_attendance)

        periodViewPager = binding.periodViewpager
        pagerAdapter = PeriodPagerAdapter(this)
        periodViewPager.adapter = pagerAdapter

        val viewModelFactory = InjectorUtils.provideSubjectListViewModelFactory(this)
        periodListViewModel = ViewModelProvider(this, viewModelFactory).get(PeriodListViewModel::class.java)
        periodListViewModel.getAllSubjectsOn(weekDay).observe(this, Observer { subjects: List<Subject> ->
            for ((title) in subjects) {
                Timber.i("title -> $title")
            }
        })
        periodListViewModel.getAllPeriodsOn(weekDay).observe(this, Observer { periods: List<Period> ->
            for (period in periods) {
                Timber.i("period $period.subjectTitle weekday $period.weekDay")
            }
            pagerAdapter.setPeriods(periods)
        })
    }

    private inner class PeriodPagerAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
        private var periods: List<Period> = ArrayList()
        override fun createFragment(position: Int): Fragment {
            return newInstance(periods[position])
        }

        override fun getItemCount(): Int {
            return periods.size
        }

        fun setPeriods(periods: List<Period>) {
            this.periods = periods
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val EXTRA_WEEKDAY = "MarkAttendanceActivity.EXTRA_WEEKDAY"
        fun newIntent(context: Context?, weekDay: Int): Intent {
            val i = Intent(context, MarkAttendanceActivity::class.java)
            i.putExtra(EXTRA_WEEKDAY, weekDay)
            return i
        }
    }
}