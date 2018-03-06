package net.pinaz993.ledger;

import android.arch.persistence.room.Ignore;

import java.util.HashMap;

/**
 * Created by Patrick Shannon on 3/6/2018.
 * A DAO for accessing  records
 */

public abstract class AttendanceDao {
    //TODO: Implement record
    public abstract void recordAttendance(String studentId, String classId, int interval,
                                          boolean present, boolean lateArrival,
                                          boolean earlyDeparture, boolean excused);

    //TODO: Implement update
    public abstract void updateAttendance(String studentId, String classId, int interval,
                                          boolean present, boolean lateArrival,
                                          boolean earlyDeparture, boolean excused);

    //TODO: Implement recordOrUpdate for use in toggling values in ClassListActivity
    @Ignore
    public void recordOrUpdate(String studentId, String classId, int interval,
                               boolean present, boolean lateArrival,
                               boolean earlyDeparture, boolean excused){
        //If there is a record for studentId, classId, and interval, then update the record.
        //otherwise, record.
    }

    //TODO: Implement getRecord
    public abstract HashMap getRecord(String studentId, String classId, int interval);

    //TODO: Implement getRecordsForStudent
    public abstract HashMap[] getRecordsForStudent(String studentId);

    //TODO: Implement getRecordsForClass
    public abstract HashMap[] getRecordsForClass(String classId);

    //TODO: Implement getRecordsForStudentInClass
    public abstract HashMap[] getRecordsForStudentInClass(String studentId, String classId);
}
