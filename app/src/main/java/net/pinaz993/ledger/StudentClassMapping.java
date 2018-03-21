package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**Room entity object to track which students are in which classes. A decidedly inelegant solution,
 * but one that does it's job. The table can be queried to find a list of student that are in a
 * specific class, or a list of classes that a student is in.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        primaryKeys = {"studentId", "classId"},
        foreignKeys = {@ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId"),
                       @ForeignKey(entity = Class.class, parentColumns = "id", childColumns = "classId")})
public class StudentClassMapping {
    @NonNull
    public String studentId;

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
    public String classId;

}
