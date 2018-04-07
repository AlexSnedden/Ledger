package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/** A Room entity object to represent a single recorded instance of a behavior, and the student and
 * class involved.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        tableName = "BehaviorRecords",
        primaryKeys = {"studentId", "classId", "behaviorId", "date"},
        foreignKeys = {
                @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId"),
                @ForeignKey(entity = Behavior.class, parentColumns = "id", childColumns = "behaviorId")
        }
)
public class BehaviorRecord {

    public String getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(String behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @NonNull
    public String behaviorId, date, studentId, classId;
}
