package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**Room entity object to track which students are in which classes. A decidedly inelegant solution,
 * but one that does it's job. The table can be queried to find a list of student that are in a
 * specific class, or a list of classes that a student is in.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        primaryKeys = {"studentId", "classId"},
        foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId")
)
public class StudentClassMapping {

    public String studentId, classId;

}
