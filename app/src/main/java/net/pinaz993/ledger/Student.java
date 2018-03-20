package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/** Room entity object that represents a student.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(tableName = "Students")
public class Student {
    @NonNull
    @PrimaryKey
    public String id;

    String firstName;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String lastName;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    String emailAddress;

    String getFullName() {return firstName + " " + lastName;}

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
