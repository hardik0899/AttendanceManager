package android.hardik.attendancemanager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "subject_table",
        indices = [ Index("title") ])
data class Subject(
    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "classes_attended")
    var attendedClasses: Int = 0,

    @ColumnInfo(name = "classes_missed")
    var missedClasses: Int = 0
) {

    fun incrementClassesAttended() {
        attendedClasses += 1
    }

    fun incrementClassesMissed() {
        missedClasses += 1
    }

    val totalClasses: Int
        get() = attendedClasses + missedClasses
}
