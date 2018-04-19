package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by A.J. on 2/25/18.
 */

@Dao
public interface ClassDao {
    String clearTableQuery = "DELETE FROM Classes";
    String getAllClassesQuery = "SELECT * FROM Classes";
    String getClassById = "SELECT * FROM Classes WHERE id = :id LIMIT 1";

    @Query(getClassById)
    Class getClassById(String id);
    @Query(getAllClassesQuery)
    Class[] getAllClasses();
    @Insert
    void addClass(Class newClass);
    @Delete
    void deleteClass(Class classObject);
    @Query(clearTableQuery)
    void clearTable();


}
