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
                                    "behaviorId = :behaviorId AND classId = :classId AND date = :date";
    String getAllBehaviorRecordsQuery = "SELECT * FROM BehaviorRecords";
    String deleteAllRecordsWithBehaviorQuery = "DELETE FROM BehaviorRecords WHERE behaviorId = :behaviorId";
    String deleteAllRecordsWithStudentQuery = "DELETE FROM BehaviorRecords WHERE studentId = :studentId";
    String deleteAllRecordsWithClassQuery = "DELETE FROM BehaviorRecords WHERE classId = :classId";


    @Insert
    void recordBehaviorRecord(BehaviorRecord behaviorRecord);
    @Delete
    void deleteBehaviorRecord(BehaviorRecord behaviorRecord);
    @Query(getBehaviorRecordQuery)
    BehaviorRecord getBehaviorRecord(String studentId, String behaviorId, String classId, String date);
    @Query(getAllBehaviorRecordsQuery)
    BehaviorRecord[] getAllBehaviorRecords();
    @Query(deleteAllRecordsWithBehaviorQuery)
    void deleteAllRecordsWithBehavior(String behaviorId);
    @Query(deleteAllRecordsWithStudentQuery)
    void deleteAllRecordsWithStudent(String studentId);
    @Query(deleteAllRecordsWithClassQuery)
    void deleteAllRecordsWithClass(String classId);
}
