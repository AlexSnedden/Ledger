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

        if(LedgerDatabase.getDb().getObjectIdsDao().getObjectIds() == null) {
            ObjectIds objectIds = new ObjectIds();
            objectIds.latestStudentId = "0";
            objectIds.latestBehaviorId = Integer.toString(Integer.MIN_VALUE);
            objectIds.latestStudentId = Integer.toString(Integer.MIN_VALUE);
            LedgerDatabase.getDb().getObjectIdsDao().addObjectIds(objectIds);
        }

        //generateDummyStudents();
        Intent redirect = new Intent(this, MainMenu.class);
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


    // FixMe: Just for dev purposes.
    private void generateDummyStudents() {
        Class _class = null;
        Student student = null;
        StudentClassMapping mapping = null;
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
        }
    }
}