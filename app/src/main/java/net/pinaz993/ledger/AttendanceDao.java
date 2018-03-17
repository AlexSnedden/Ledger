package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
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
                            "classId = :classId AND interval = :interval LIMIT 1;";
    final String getRecordsForStudentQuery = "SELECT * FROM AttendanceRecords WHERE studentId = :studentId;";
    final String getRecordsForClassQuery = "SELECT * FROM AttendanceRecords WHERE classId = :classId;";
    final String getRecordsForStudentInClassQuery = "SELECT * FROM AttendanceRecords WHERE " +
                                                    "studentId = :studentId AND classId = :classId;";
    @Insert
    public abstract void recordAttendance(Attendance attendance);

    @Update
    public abstract void updateAttendance(Attendance attendance);

    @Ignore
    public void recordOrUpdate(Attendance attendance) {
        if(getRecord(attendance.studentId, attendance.classId, attendance.interval) == null) {
            recordAttendance(attendance);
        } else {
            updateAttendance(attendance);
        }
    }


    @Query(getRecordQuery)
    public abstract Attendance getRecord(String studentId, String classId, int interval);

    @Query(getRecordsForStudentQuery)
    public abstract Attendance[] getRecordsForStudent(String studentId);

    @Query(getRecordsForClassQuery)
    public abstract Attendance[] getRecordsForClass(String classId);

    @Query(getRecordsForStudentInClassQuery)
    public abstract Attendance[] getRecordsForStudentInClass(String studentId, String classId);
}
