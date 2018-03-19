package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.joda.time.DateTime;

import java.util.concurrent.ExecutionException;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ledger");

        Button takeAttendanceBtn = findViewById(R.id.takeAttendanceBtn);
        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        takeAttendanceBtn.setText(String.format("Take attendance for %s", currentDate));
        takeAttendanceBtn.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     takeAttendance();
                                                 }
                                             }
        );
    }

    private void takeAttendance() {
        Intent redirect = new Intent(this, ClassListActivity.class);
        String classes[] = LedgerDatabase.getDb().getClassDao().getAllClasses();
        Bundle classListActivityBundle = new Bundle();
        classListActivityBundle.putString("CLASS_ID", classes[0]);
        redirect.putExtras(classListActivityBundle);
        startActivity(redirect);
    }

}