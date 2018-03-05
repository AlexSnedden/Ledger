package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

/**
 * Data Access Object  to handle all persistent data needs for Students table.
 * Created by A.J. on 2/8/18.
 */
@Dao
public interface StudentClassMappingDao {
    String getAllStudentsInClassQuery = "SELECT studentId FROM StudentClassMapping " +
                                        "WHERE classId = :classId";
    String clearTableQuery = "DELETE FROM StudentClassMapping";

    @Query(getAllStudentsInClassQuery)
    String[] getAllStudentsInClass(String classId);
    @Query(clearTableQuery)
    void clearTable();
}
