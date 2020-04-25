package android.hardik.attendancemanager.data.models

import androidx.room.Embedded
import androidx.room.Relation

class SubjectWithPeriods {
    @Embedded
    var subject: Subject? = null
    @Relation(parentColumn = "title", entityColumn = "subject_title")
    var periods: List<Period>? = null
}