package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/** Room entity object to represent a single student attending a singe class. Interval represents
 * the attendance interval that the attendance is being recorded for.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        tableName = "AttendanceRecords", primaryKeys = {"studentId", "classId", "interval"},
        foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId")
)
public class Attendance {
    @NonNull
    public String studentId, classId;
    @NonNull
    public int interval;

    public boolean present, lateArrival, earlyDeparture, excused;

}
