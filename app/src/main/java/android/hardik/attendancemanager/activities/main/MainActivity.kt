package android.hardik.attendancemanager.activities.main

import android.hardik.attendancemanager.activities.SingleFragmentActivity
import android.hardik.attendancemanager.activities.main.fragments.SubjectListFragment
import androidx.fragment.app.Fragment

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment? {
        return SubjectListFragment.newInstance()
    }
}