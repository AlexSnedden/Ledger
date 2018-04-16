package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ObjectIdsDao {
    String getObjectIdsQuery = "SELECT * FROM ObjectIds LIMIT 1";
    String clearTableQuery = "DELETE FROM  ObjectIds";

    @Query(getObjectIdsQuery)
    ObjectIds getObjectIds();
    @Query(clearTableQuery)
    void clearTable();
    @Insert
    void addObjectIds(ObjectIds objectIds);
}
