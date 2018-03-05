package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        LedgerDatabase.setInstance(Room.databaseBuilder(getApplicationContext(),
                                   LedgerDatabase.class, "ledger_db").allowMainThreadQueries().build());

        Intent redirect = new Intent(this, AddClassActivity.class);

        // JUST FOR DEV PURPOSES REMOVE THIS HOLY CRAP
        LedgerDatabase.getDb().getClassDao().clearTable();
        LedgerDatabase.getDb().getStudentDao().clearTable();

        startActivity(redirect);
    }
}