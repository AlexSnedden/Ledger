package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

        Intent redirect = new Intent(this, MainMenu.class);
        startActivity(redirect);
        Behavior b1 = new Behavior();
        b1.setId("gsdgfgsfgdsdgsdhsdbmuj");
        b1.setName("b1");
        Behavior b2 = new Behavior();
        b2.setId("afsdfwivnfvbeuvbsuvbek");

        //LedgerDatabase.getDb().getBehaviorDao().addBehavior(b1);
        //LedgerDatabase.getDb().getBehaviorDao().addBehavior(b2);
    }

    private class CreateDb extends AsyncTask<Object, Void, Object> {
        @Override
        protected Object doInBackground(Object[] objects) {
            LedgerDatabase.setInstance(Room.databaseBuilder(getApplicationContext(),
                    LedgerDatabase.class, "ledger_db").allowMainThreadQueries().build());
            return new Object();
        }
    }


    // FixMe: Just for dev purposes.
    private void generateDummyStudents() {
        Class _class = null;
        Student student = null;
        StudentClassMapping mapping = null;
        int l = 100;
        for(int i=0; i < 1; i++) {
            _class = new Class();
            _class.setId(String.format("Class %d", i));
            LedgerDatabase.getDb().getClassDao().addClass(_class);
            String[] names = {"Abe", "Carlo", "Sylvester", "Charlie", "Beth", "Christina", "Chloe", "Liz", "Yemen", "Chung"};

            for(int j=0; j < names.length; j++) {
                student = new Student();
                student.setId(Integer.toString(j));
                student.setLastName(names[j]);
                student.setFirstName(names[j]);
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
