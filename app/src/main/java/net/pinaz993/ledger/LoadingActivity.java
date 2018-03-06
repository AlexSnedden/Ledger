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

        for(int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setEmailAddress("email@address.com");
            student.setFirstName("Richie");
            student.setLastName("Rich");
            student.setId(Integer.toString(i));
            LedgerDatabase.getDb().getStudentDao().addStudent(student);
        }

        Intent redirect = new Intent(this, AddClassActivity.class);
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