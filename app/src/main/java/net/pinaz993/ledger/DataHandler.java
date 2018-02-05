package net.pinaz993.ledger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Data Access Object  to handle all persistent data needs.
 * Created by A.J. on 2/5/18.
 */

@Dao
public interface DataHandler {
    // <editor-fold defaultstate="collapsed" desc="Student and class manipulation SQL strings"
    public String getAllClassesQuery = "SELECT DISTINCT classId from StudentClassMapping;";
    public String getAllStudentsQuery = "SELECT * FROM Student;";
    public String getClassStudentsQuery = "SELECT studentId FROM StudentClassMapping " +
            "WHERE classId = :classId;";
    public String getStudentsClassesQuery = "SELECT classId from StudentClassMapping " +
            " WHERE studentId = :studentId;";
    public String deleteClassQuery = "DELETE FROM StudentClassMapping WHERE classId = :classId;";
    public String deleteStudentQuery = "DELETE FROM StudentClassMapping WHERE studentId = :studentId; " +
            "DELETE FROM Student WHERE studentId = :studentId;";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student and class manipulation">

    @Query(getAllClassesQuery)
    public String[] getAllClasses();
    @Query(getAllStudentsQuery)
    public String[] getAllStudents();

    @Query(getClassStudentsQuery)
    public String[] getClassStudents(String classId);
    @Query(getStudentsClassesQuery)
    public String[] getStudentsClasses(String studentId);

    @Query(deleteClassQuery)
    public void deleteClass(String classId);
    @Query(deleteStudentQuery)
    public void deleteStudent(String studentId);

    @Insert
    public void addStudent(Student student);

    @Delete
    public void setRemoveStudentFromClass(StudentClassMapping studentClassMapping);
    @Insert
    public void enrollStudent(StudentClassMapping studentClassMapping);
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Behavior manipulation">
    @Insert
    public void addBehavior(Behavior behavior);
    @Delete
    public void removeBehavior(Behavior behavior);
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Behavior record manipulation">

    // </editor-fold>
}
