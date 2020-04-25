package android.hardik.attendancemanager.utilities

import android.hardik.attendancemanager.data.models.Subject
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("attendanceStatFormatted")
fun TextView.setAttendanceStatFormatted(item: Subject) {
    text = formatAttendanceStat(item, context.resources)
}

@BindingAdapter("attendancePercentageFormatted")
fun TextView.setAttendancePercentage(item: Subject) {
    text = formatAttendancePercentage(item, context.resources)
}

@BindingAdapter("attendanceProgressFormatted")
fun ProgressBar.setAttendanceProgress(item: Subject) {
    progress = formatAttendanceProgress(item, context.resources)
}