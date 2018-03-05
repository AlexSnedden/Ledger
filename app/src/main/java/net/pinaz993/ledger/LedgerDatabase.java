package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by A.J. on 2/21/18.
 */
@Database(version = 2, entities = {Student.class, Behavior.class, StudentClassMapping.class,
                                   Attendance.class, Class.class})
public abstract class LedgerDatabase extends RoomDatabase {
    private static LedgerDatabase instance = null;
    public abstract StudentDao getStudentDao();
    public abstract ClassDao getClassDao();
    public abstract StudentClassMappingDao getStudentClassMappingDao();
    static void setInstance(LedgerDatabase db) {
        instance = db;
    }
    public static LedgerDatabase getDb() {
        return instance;
    }
}
