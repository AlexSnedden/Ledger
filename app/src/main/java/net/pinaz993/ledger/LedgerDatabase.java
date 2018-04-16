package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(version = 1, entities = {Student.class, Behavior.class, BehaviorRecord.class,
                                   StudentClassMapping.class, Attendance.class, Class.class,
                                   ObjectIds.class})
abstract class LedgerDatabase extends RoomDatabase {
    private static LedgerDatabase instance = null;
    abstract StudentDao getStudentDao();
    abstract ClassDao getClassDao();
    abstract StudentClassMappingDao getStudentClassMappingDao();
    abstract AttendanceDao getAttendanceDao();
    abstract BehaviorDao getBehaviorDao();
    abstract BehaviorRecordDao getBehaviorRecordDao();
    abstract ObjectIdsDao getObjectIdsDao();
    static void setInstance(LedgerDatabase db) {
        instance = db;
    }
    static LedgerDatabase getDb() {
        return instance;
    }
}
