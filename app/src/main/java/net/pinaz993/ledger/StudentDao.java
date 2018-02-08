package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Data Access Object  to handle all persistent data needs for Students table.
 * Created by A.J. on 2/5/18.
 */

@Dao
public interface StudentDao {
    String getStudentQuery = "SELECT * FROM Students WHERE id = :studentId LIMIT 1;";
    String clearTableQuery = "DELETE FROM Students";

    /* Deletes all entry from the database */
    @Query(clearTableQuery)
    void clearTable();

    /* Inserts a student into the database */
    @Insert
    void addStudent(Student student);

    /* Retrieves information for a student in the database */
    @Query(getStudentQuery)
    Student getStudent(String studentId);

}
