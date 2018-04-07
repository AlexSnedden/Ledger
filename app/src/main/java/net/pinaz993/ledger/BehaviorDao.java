package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Data Access Object  to handle all persistent data needs for Behaviors table.
 * Created by A.J. on 2/8/18.
 */

@Dao
public interface BehaviorDao {
    String getAllBehaviorsQuery = "SELECT * FROM Behaviors";

    @Insert
    void addBehavior(Behavior behavior);
    @Query(getAllBehaviorsQuery)
    Behavior[] getAllBehaviors();

}
