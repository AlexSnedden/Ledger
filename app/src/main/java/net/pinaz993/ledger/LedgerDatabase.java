package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by A.J. on 2/21/18.
 */
@Database(version = 1, entities = {Student.class, Behavior.class, StudentClassMapping.class,
                                   Attendance.class, Classes.class})
public abstract class LedgerDatabase extends RoomDatabase {
    private static LedgerDatabase instance = null;
    static void setInstance(LedgerDatabase db) {
        instance = db;
    }
    public static LedgerDatabase getDb() {
        return instance;
    }
}
