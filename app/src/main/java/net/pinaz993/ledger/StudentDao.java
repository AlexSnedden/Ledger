package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;

/**
 * Data Access Object  to handle all persistent data needs for Students table.
 * Created by A.J. on 2/5/18.
 */

@Dao
public interface StudentDao {
    String getStudentQuery = "SELECT * FROM Students WHERE id = :studentId LIMIT 1;";
    String clearTableQuery = "DELETE FROM Students";
    String getAllStudentsQuery = "SELECT * FROM Students";

    /* Deletes all entry from the database */
    @Query(clearTableQuery)
    public abstract void clearTable();

    /* Inserts a student into the database */
    @Insert
    public abstract void addStudent(Student student);

    /* Retrieves information for a student in the database */
    @Query(getStudentQuery)
    public abstract Student getStudent(String studentId);

    @Query(getAllStudentsQuery)
    public abstract Student[] getAllStudents();

}
