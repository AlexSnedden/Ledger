package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by A.J. on 2/25/18.
 */

@Dao
public interface ClassDao {
    String clearTableQuery = "DELETE FROM classes";

    @Insert
    public void addClass(Class newClass);
    @Query(clearTableQuery)
    public void clearTable();
}