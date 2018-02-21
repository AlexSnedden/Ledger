package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
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

        LedgerDatabase.setInstance(Room.databaseBuilder(getApplicationContext(),
                                   LedgerDatabase.class, "ledger_db").build());

        Intent redirect = new Intent(this, AddStudentActivity.class);

        startActivity(redirect);
    }
}