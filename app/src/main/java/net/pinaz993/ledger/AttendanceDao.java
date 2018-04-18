package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.HashMap;

/**
 * Created by Patrick Shannon on 3/6/2018.
 * A DAO for accessing  records
 */
@Dao
public interface AttendanceDao {
    String getRecordQuery = "SELECT * FROM AttendanceRecords WHERE studentId = :studentId AND " +
                            "classId = :classId AND date = :date LIMIT 1;";
    String getRecordsForStudentQuery = "SELECT * FROM AttendanceRecords WHERE studentId = :studentId;";
    String getRecordsForClassQuery = "SELECT * FROM AttendanceRecords WHERE classId = :classId;";
    String getRecordsForStudentInClassQuery = "SELECT * FROM AttendanceRecords WHERE " +
                                                    "studentId = :studentId AND classId = :classId;";
    String clearTableQuery = "DELETE FROM AttendanceRecords";
    String deleteRecordsWithStudentQuery = "DELETE FROM AttendanceRecords WHERE studentId = :studentId";
    String deleteRecordsWithClassQuery = "DELETE FROM AttendanceRecords WHERE classId = :classId";


    @Delete
    void deleteAttendance(Attendance attendance);

    @Insert
    void recordAttendance(Attendance attendance);

    @Update
    void updateAttendance(Attendance attendance);

    @Query(getRecordQuery)
    Attendance getRecord(String studentId, String classId, String date);

    @Query(getRecordsForStudentQuery)
    Attendance[] getRecordsForStudent(String studentId);

    @Query(getRecordsForClassQuery)
    Attendance[] getRecordsForClass(String classId);

    @Query(getRecordsForStudentInClassQuery)
    Attendance[] getRecordsForStudentInClass(String studentId, String classId);

    @Query(clearTableQuery)
    void clearTable();

    @Query(deleteRecordsWithClassQuery)
    void deleteRecordsWithClass(String classId);

    @Query(deleteRecordsWithStudentQuery)
    void deleteRecordsWithStudent(String studentId);
}
