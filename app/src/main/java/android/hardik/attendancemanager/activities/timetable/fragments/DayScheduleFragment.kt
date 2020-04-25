package android.hardik.attendancemanager.activities.timetable.fragments

import android.content.Context
import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.activities.timetable.viewmodels.PeriodListViewModel
import android.hardik.attendancemanager.activities.timetable.viewmodels.PeriodViewModel
import android.hardik.attendancemanager.databinding.FragmentDayScheduleBinding
import android.hardik.attendancemanager.databinding.ListItemPeriodBinding
import android.hardik.attendancemanager.data.models.Period
import android.hardik.attendancemanager.utilities.InjectorUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DayScheduleFragment private constructor(private val weekDay: Int) : Fragment() {

    private lateinit var periodListViewModel: PeriodListViewModel
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onAddPeriod(periodNumber: Int, weekDay: Int)
        fun onModifyPeriod(period: Period)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val viewModelFactory = InjectorUtils.providePeriodListViewModelFactory(requireActivity())
        periodListViewModel = ViewModelProvider(this, viewModelFactory).get(PeriodListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDayScheduleBinding>(
                inflater, R.layout.fragment_day_schedule, container, false)
        val adapter = PeriodAdapter()
        periodListViewModel.getAllPeriodsOn(weekDay).observe(viewLifecycleOwner, Observer { periods: List<Period> ->
            adapter.setPeriodList(periods)
        })
        binding.periodListRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.periodListRecyclerView.adapter = adapter
        return binding.root
    }

    private inner class PeriodHolder(private val binding: ListItemPeriodBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var mPeriod: Period? = null
        fun bind(period: Period) {
            mPeriod = period
            binding.periodViewModel?.setPeriod(period)
            binding.periodViewModel?.notifyChange()
            binding.executePendingBindings()
        }

        override fun onClick(v: View) {
            if (mPeriod!!.subjectTitle == "-") {
                callbacks!!.onAddPeriod(mPeriod!!.periodNumber, weekDay)
            } else {
                callbacks!!.onModifyPeriod(mPeriod!!)
            }
        }

        init {
            binding.periodViewModel = PeriodViewModel()
            itemView.setOnClickListener(this)
        }
    }

    private inner class PeriodAdapter : RecyclerView.Adapter<PeriodHolder>() {
        private var periods: MutableList<Period> = ArrayList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodHolder {
            val binding = DataBindingUtil.inflate<ListItemPeriodBinding>(
                    layoutInflater, R.layout.list_item_period, parent, false)
            return PeriodHolder(binding)
        }

        override fun onBindViewHolder(holder: PeriodHolder, position: Int) {
            holder.bind(periods[position])
        }

        override fun getItemCount(): Int {
            return periods.size
        }

        /**
         * modifies period list by replacing same period number period from
         * periods
         * @param periods : new period list whenever it is changed
         */
        fun setPeriodList(periods: List<Period>) { // i know not a good implementation
            for (i in 0 until MAX_PERIODS) {
                this.periods[i].subjectTitle = "-"
                for (period in periods) {
                    if (this.periods[i].periodNumber == period.periodNumber) {
                        this.periods[i] = period
                    }
                }
            }
            notifyDataSetChanged()
        }

        init {
            for (i in 0 until MAX_PERIODS) {
                periods.add(Period("-", i + 1, weekDay))
            }
        }
    }

    companion object {
        private const val MAX_PERIODS = 8
        fun newInstance(weekDay: Int): DayScheduleFragment {
            return DayScheduleFragment(weekDay)
        }
    }
}