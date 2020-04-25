package android.hardik.attendancemanager.activities.timetable

import android.content.Context
import android.content.Intent
import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.activities.timetable.fragments.DayScheduleFragment
import android.hardik.attendancemanager.dialogs.PeriodDialogFragment
import android.hardik.attendancemanager.data.models.Period
import android.hardik.attendancemanager.utilities.InjectorUtils
import android.hardik.attendancemanager.activities.timetable.viewmodels.PeriodListViewModel
import android.hardik.attendancemanager.activities.main.viewmodels.SubjectsViewModel
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_timetable.*

import java.util.Calendar
import kotlin.collections.ArrayList
import kotlin.collections.List

class TimeTableActivity : AppCompatActivity(),
        DayScheduleFragment.Callbacks, PeriodDialogFragment.Callbacks {
    private lateinit var viewPager: ViewPager2

    private lateinit var mSubjectsViewModel: SubjectsViewModel
    private lateinit var periodListViewModel: PeriodListViewModel

    private lateinit var allSubjectsTitles: List<String>

    override val subjectTitles: List<String>
        get() = allSubjectsTitles

    private var weekDay = -1
    private var weekDayOffSet = -1

    private var tempPeriod: Period? = null
    private var addNewPeriod: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        createWeekDayHash()
        weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        mSubjectsViewModel = ViewModelProvider(
                this,
                InjectorUtils.provideSubjectListViewModelFactory(this)
        ).get(SubjectsViewModel::class.java)

        mSubjectsViewModel.subjectTitles.observe(this,
                Observer { subjectTitles: List<String> -> allSubjectsTitles = subjectTitles })

        periodListViewModel = ViewModelProvider(
                this,
                InjectorUtils.providePeriodListViewModelFactory(this)
        ).get(PeriodListViewModel::class.java)

        viewPager = day_schedule_viewpager
        viewPager.adapter = DayScheduleAdapter(this)
        viewPager.currentItem = weekDay - weekDayOffSet
        val tabLayout: TabLayout = tab_layout_weekday
        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position -> tab.text = WEEK_DAYS.get(position + weekDayOffSet) }.attach()
    }

    private inner class DayScheduleAdapter(fragmentActivity: FragmentActivity)
        : FragmentStateAdapter(fragmentActivity) {

        var weekDays: MutableList<Int> = ArrayList()
        init {
            for (i in 2..7) {
                weekDays.add(i)
            }
            weekDayOffSet = weekDays[0]
        }

        override fun createFragment(position: Int): Fragment {
            return DayScheduleFragment.newInstance(weekDays[position])
        }

        override fun getItemCount(): Int {
            return weekDays.size
        }
    }

    private fun openAddPeriodDialog(periodTitle: String?) {
        val dialogFragment = PeriodDialogFragment.newInstance(periodTitle)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        transaction
//                .add(android.R.id.content, dialogFragment)
//                .addToBackStack(null)
//                .commit()
        val fm = supportFragmentManager
        dialogFragment.show(fm, "period")
    }

    override fun onAddPeriod(periodNumber: Int, weekDay: Int) {
        addNewPeriod = true
        tempPeriod = Period(null, periodNumber, weekDay)
        openAddPeriodDialog(tempPeriod!!.subjectTitle)
    }

    override fun onModifyPeriod(period: Period) {
        addNewPeriod = false
        tempPeriod = period
        openAddPeriodDialog(period.subjectTitle)
    }

    override fun onDeletePeriod(title: String?) {
        periodListViewModel.deletePeriod(tempPeriod!!.periodNumber, tempPeriod!!.weekDay)
    }

    override fun onPeriodSelected(title: String?) {
        tempPeriod!!.subjectTitle = title
        if (addNewPeriod) {
            periodListViewModel.insert(tempPeriod!!)
        } else {
            periodListViewModel.update(tempPeriod!!)
        }
        tempPeriod = null
        addNewPeriod = false
    }

    companion object {
        lateinit var WEEK_DAYS: SparseArray<String>

        fun newIntent(context: Context): Intent {
            return Intent(context, TimeTableActivity::class.java)
        }

        private fun createWeekDayHash() {
            WEEK_DAYS = SparseArray()
            WEEK_DAYS.put(1, "sunday")
            WEEK_DAYS.put(2, "monday")
            WEEK_DAYS.put(3, "tuesday")
            WEEK_DAYS.put(4, "wednesday")
            WEEK_DAYS.put(5, "thursday")
            WEEK_DAYS.put(6, "friday")
            WEEK_DAYS.put(7, "saturday")
        }
    }
}
