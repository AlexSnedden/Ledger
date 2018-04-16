package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Data Access Object  to handle all persistent data needs for BehaviorRecords table.
 * Created by A.J. on 2/8/18.
 */

@Dao
public interface BehaviorRecordDao {
    String getBehaviorRecordQuery = "SELECT * FROM BehaviorRecords WHERE studentId = :studentId AND "+
                                    "behaviorId = :behaviorId AND classId = :classId AND date = :date;";

    @Insert
    void recordBehaviorRecord(BehaviorRecord behaviorRecord);
    @Delete
    void deleteBehaviorRecord(BehaviorRecord behaviorRecord);
    @Query(getBehaviorRecordQuery)
    BehaviorRecord getBehaviorRecord(String studentId, String behaviorId, String classId, String date);

}