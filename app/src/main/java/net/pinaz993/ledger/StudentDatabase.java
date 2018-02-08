package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by A.J. on 2/5/18.
 */

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao getDao();
    public static StudentDatabase getDb(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), StudentDatabase.class,
                                            "student-db").allowMainThreadQueries().build();
    }
}

