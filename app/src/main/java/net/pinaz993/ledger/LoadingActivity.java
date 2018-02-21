package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Context context = getApplicationContext();

        /* Initialize DataHandler with all databases and dao's. */
        DataHandler dh = DataHandler.getDataHandler();

        dh.studentDatabase = StudentDatabase.getDb(context);
        dh.studentDao = dh.studentDatabase.getDao();

        dh.behaviorDatabase = BehaviorDatabase.getDb(context);
        dh.behaviorDao = dh.behaviorDatabase.getDao();

        dh.studentClassMappingDatabase = StudentClassMappingDatabase.getDb(context);
        dh.studentClassMappingDao = dh.studentClassMappingDatabase.getDao();

        Student student =  new Student();
        dh.studentDao.clearTable();
        student.setId("abc");
        student.setFirstName("Bump");
        student.setLastName("Boom");
        student.setEmailAddress("zoom");
        dh.studentDao.addStudent(student);
        Student retrievedStudent = dh.studentDao.getStudent(student.id);
        Log.v("tag" ,String.format("%s", retrievedStudent.getFirstName()));
    }
}