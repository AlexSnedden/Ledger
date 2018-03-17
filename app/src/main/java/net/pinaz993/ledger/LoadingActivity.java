package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        Intent redirect = new Intent(this, ClassListActivity.class);
        Bundle classListActivityBundle = new Bundle();
        classListActivityBundle.putString("CLASS_ID", "test");
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

    private boolean isFirstLaunch() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(getString(R.string.previously_launched), Context.MODE_PRIVATE);
        return getResources().getBoolean(R.string.previously_launched);
    }
    private void recordFirstLaunch() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(getString(R.string.previously_launched), Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(getString(R.string.previously_launched), true);
        e.commit();
    }
    // FixMe: Just for dev purposes.
    private void generateDummyStudents() {
        LedgerDatabase.getDb().getStudentClassMappingDao().clearTable();
        LedgerDatabase.getDb().getClassDao().clearTable();
        LedgerDatabase.getDb().getStudentDao().clearTable();
        Class _class = null;
        Student student = null;
        StudentClassMapping mapping = null;
        int l = 100;
        for(int i=0; i < 10; i++) {
            _class = new Class();
            _class.setId(String.format("Class %d", i));
            LedgerDatabase.getDb().getClassDao().addClass(_class);
            for(int j=0; j < 20; j++) {
                student = new Student();
                student.setId(Integer.toString(j + l));
                student.setLastName(String.format("Student%d", j));
                student.setFirstName(String.format("Class%d", i));
                student.setEmailAddress("");
                mapping = new StudentClassMapping();
                mapping.setStudentId(student.getId());
                mapping.setClassId(_class.id);
                LedgerDatabase.getDb().getStudentDao().addStudent(student);
                LedgerDatabase.getDb().getStudentClassMappingDao().addStudentClassMapping(mapping);
            }
            l += 412432;
        }
    }
}