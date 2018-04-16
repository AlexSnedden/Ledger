package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static net.pinaz993.ledger.LedgerDatabase.getDb;

@Entity(tableName = "ObjectIds", primaryKeys = {"latestStudentId", "latestBehaviorId"})
public class ObjectIds {
    @NonNull
    public String latestStudentId, latestBehaviorId;

    static String getLatestStudentId() {
        return getDb().getObjectIdsDao().getObjectIds().latestStudentId;
    }
    static String getLatestBehaviorId() {
        return getDb().getObjectIdsDao().getObjectIds().latestBehaviorId;
    }
    static void updateStudentId() {
        ObjectIds objectIds = LedgerDatabase.getDb().getObjectIdsDao().getObjectIds();
        objectIds.latestStudentId = Integer.toString(Integer.parseInt(objectIds.latestStudentId) + 1);
        LedgerDatabase.getDb().getObjectIdsDao().clearTable();
        LedgerDatabase.getDb().getObjectIdsDao().addObjectIds(objectIds);
    }
    static void updateBehaviorId() {
        ObjectIds objectIds = LedgerDatabase.getDb().getObjectIdsDao().getObjectIds();
        objectIds.latestBehaviorId = Integer.toString(Integer.parseInt(objectIds.latestBehaviorId) + 1);
        LedgerDatabase.getDb().getObjectIdsDao().clearTable();
        LedgerDatabase.getDb().getObjectIdsDao().addObjectIds(objectIds);
    }
}
