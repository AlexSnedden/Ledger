package net.pinaz993.ledger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by A.J. on 2/8/18.
 */

@Database(entities = {Behavior.class}, version = 1)
public abstract class BehaviorDatabase extends RoomDatabase {
    public abstract BehaviorDao getDao();
    public static BehaviorDatabase getDb(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), BehaviorDatabase.class,
                "behavior-db").allowMainThreadQueries().build();
    }
}
