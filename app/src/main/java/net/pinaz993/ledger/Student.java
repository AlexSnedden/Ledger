package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/** Room entity object that represents a student.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(tableName = "Students")
public class Student {
    @PrimaryKey
    public String id;

    public String firstName, lastName, emailAddress;

    public String getFullName() {return firstName + " " + lastName;}
}
