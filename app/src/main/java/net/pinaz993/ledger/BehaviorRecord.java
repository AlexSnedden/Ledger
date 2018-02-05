package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/** A Room entity object to represent a single recorded instance of a behavior, and the student and
 * class involved.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(
        tableName = "BehaviorRecords",
        primaryKeys = {"studentId", "classId", "behaviorId", "timeStamp"},
        foreignKeys = {
                @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId"),
                @ForeignKey(entity = Behavior.class, parentColumns = "id", childColumns = "behaviorId")
        }
)
public class BehaviorRecord {

    public int behaviorId, timeStamp;

    public String studentId, classId;
}
