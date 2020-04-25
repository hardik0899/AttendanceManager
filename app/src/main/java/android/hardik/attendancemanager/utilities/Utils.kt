package android.hardik.attendancemanager.utilities

import android.content.res.Resources
import android.hardik.attendancemanager.R
import android.hardik.attendancemanager.data.models.Subject

fun formatAttendanceStat(subject: Subject, resources: Resources): String {
    return resources.getString(R.string.attendance_status, subject.attendedClasses, subject.totalClasses)
}

fun formatAttendancePercentage(subject: Subject, resources: Resources): String {
    return if (subject.totalClasses == 0) {
        resources.getString(R.string.attendance_percentage, 0.0)
    } else {
        val percentage = (subject.attendedClasses.toDouble() / subject.totalClasses) * 100
        resources.getString(R.string.attendance_percentage, percentage)
    }
}

fun formatAttendanceProgress(subject: Subject, resources: Resources): Int {
    return if (subject.totalClasses == 0) {
        0
    } else {
        (subject.attendedClasses * 100) / subject.totalClasses
    }
}