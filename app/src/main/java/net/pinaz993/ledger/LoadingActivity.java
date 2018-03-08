package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        CreateDb createDbLock = new CreateDb();
        createDbLock.execute();
        try {
            createDbLock.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //FIXME: JUST FOR DEV PURPOSES REMOVE THIS HOLY CRAP
        LedgerDatabase.getDb().getClassDao().clearTable();
        LedgerDatabase.getDb().getStudentDao().clearTable();
        LedgerDatabase.getDb().getStudentClassMappingDao().clearTable();

        // for ClassListActivity debugging purposes

        Class newClass = new Class();
        newClass.setId("AP English");
        Class anotherClass = new Class();
        anotherClass.setId("Algebra");
        LedgerDatabase.getDb().getClassDao().addClass(anotherClass);
        LedgerDatabase.getDb().getClassDao().addClass(newClass);
        Student apEngStudent = new Student();
        apEngStudent.setId("0103431");
        apEngStudent.setFirstName("Foo");
        apEngStudent.setLastName("Bar");
        StudentClassMapping classMapping = new StudentClassMapping();
        classMapping.setClassId(newClass.id);
        classMapping.setStudentId(apEngStudent.getId());

        Intent redirect = new Intent(this, ClassListActivity.class);
        Bundle classListActivityBundle = new Bundle();
        classListActivityBundle.putString("CLASS_ID", "AP English");
        redirect.putExtras(classListActivityBundle);
        startActivity(redirect);
    }

    private class CreateDb extends AsyncTask<Object, Void, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            LedgerDatabase.setInstance(Room.databaseBuilder(getApplicationContext(),
                    LedgerDatabase.class, "ledger_db").allowMainThreadQueries().build());
            return new Object();
        }
    }
}