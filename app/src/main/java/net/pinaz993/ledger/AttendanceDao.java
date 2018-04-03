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
public abstract class AttendanceDao {
    final String getRecordQuery = "SELECT * FROM AttendanceRecords WHERE studentId = :studentId AND " +
                            "classId = :classId AND date = :date LIMIT 1;";
    final String getRecordsForStudentQuery = "SELECT * FROM AttendanceRecords WHERE studentId = :studentId;";
    final String getRecordsForClassQuery = "SELECT * FROM AttendanceRecords WHERE classId = :classId;";
    final String getRecordsForStudentInClassQuery = "SELECT * FROM AttendanceRecords WHERE " +
                                                    "studentId = :studentId AND classId = :classId;";
    final String clearTableQuery = "DELETE FROM AttendanceRecords";

    @Delete
    public abstract void deleteAttendance(Attendance attendance);

    @Insert
    public abstract void recordAttendance(Attendance attendance);

    @Update
    public abstract void updateAttendance(Attendance attendance);

    @Query(getRecordQuery)
    public abstract Attendance getRecord(String studentId, String classId, String date);

    @Query(getRecordsForStudentQuery)
    public abstract Attendance[] getRecordsForStudent(String studentId);

    @Query(getRecordsForClassQuery)
    public abstract Attendance[] getRecordsForClass(String classId);

    @Query(getRecordsForStudentInClassQuery)
    public abstract Attendance[] getRecordsForStudentInClass(String studentId, String classId);

    @Query(clearTableQuery)
    public abstract void clearTable();
}
