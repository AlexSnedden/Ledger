package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by A.J. on 2/21/18.
 * Go away, IDE. I'm trying to work here.
 */
@Database(version = 1, entities = {Student.class, Behavior.class, BehaviorRecord.class,
                                   StudentClassMapping.class, Attendance.class, Class.class})
abstract class LedgerDatabase extends RoomDatabase {
    private static LedgerDatabase instance = null;
    abstract StudentDao getStudentDao();
    abstract ClassDao getClassDao();
    abstract StudentClassMappingDao getStudentClassMappingDao();
    abstract AttendanceDao getAttendanceDao();
    abstract BehaviorDao getBehaviorDao();
    abstract BehaviorRecordDao getBehaviorRecordDao();
    static void setInstance(LedgerDatabase db) {
        instance = db;
    }
    static LedgerDatabase getDb() {
        return instance;
    }
}
