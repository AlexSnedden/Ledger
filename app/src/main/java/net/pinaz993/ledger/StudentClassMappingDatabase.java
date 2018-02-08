package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by A.J. on 2/8/18.
 */

@Database(entities = {StudentClassMapping.class}, version = 1)
public abstract class StudentClassMappingDatabase extends RoomDatabase {
    public abstract StudentClassMappingDao getDao();
    public static StudentClassMappingDatabase getDb(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), StudentClassMappingDatabase.class,
                "student-class-mapping-db").allowMainThreadQueries().build();
    }
}