package android.hardik.attendancemanager.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "period_table",
        foreignKeys = [
            ForeignKey(
                    entity = Subject::class,
                    parentColumns = ["title"],
                    childColumns = ["subject_title"],
                    onUpdate = CASCADE,
                    onDelete = CASCADE
            )
        ]
)
class Period(@field:ColumnInfo(name = "subject_title") var subjectTitle: String?,
             @field:ColumnInfo(name = "period_number") var periodNumber: Int,
             @field:ColumnInfo(name = "week_day") var weekDay: Int) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var periodId: Long = 0
}