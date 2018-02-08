package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by A.J. on 2/8/18.
 */

@Database(entities = {AttendanceInterval.class}, version = 1)
public abstract class AttendanceIntervalDatabase extends RoomDatabase {
    public abstract AttendanceIntervalDao getDao();
    public static AttendanceIntervalDatabase getDb(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AttendanceIntervalDatabase.class,
                "behavior-db").allowMainThreadQueries().build();
    }
}