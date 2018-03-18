package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/** Room entity object to represent a single student attending a singe class. Interval represents
 * the attendance interval that the attendance is being recorded for.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        tableName = "AttendanceRecords", primaryKeys = {"studentId", "classId", "date"},
        foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId")
)
public class Attendance {
    @NonNull
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(@NonNull String studentId) {
        this.studentId = studentId;
    }

    @NonNull
    public String getClassId() {
        return classId;
    }

    public void setClassId(@NonNull String classId) {
        this.classId = classId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isLateArrival() {
        return lateArrival;
    }

    public void setLateArrival(boolean lateArrival) {
        this.lateArrival = lateArrival;
    }

    public boolean isEarlyDeparture() {
        return earlyDeparture;
    }

    public void setEarlyDeparture(boolean earlyDeparture) {
        this.earlyDeparture = earlyDeparture;
    }

    public boolean isExcused() {
        return excused;
    }

    public void setExcused(boolean excused) {
        this.excused = excused;
    }

    @NonNull
    public String studentId, classId;
    @NonNull
    // takes the form "MM-YYYY-DD"
    public String date;

    public boolean present, lateArrival, earlyDeparture, excused = false;
}
