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
        Button addClassBtn = findViewById(R.id.addAClassBtn);
        Button editStudentsBtn = findViewById(R.id.editStudentsBtn);
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
        });
        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass();
            }
        });
        editStudentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editStudents();
            }
        });


    }

    private void takeAttendance() {
        Intent redirect = new Intent(this, ClassListActivity.class);
        Class classes[] = LedgerDatabase.getDb().getClassDao().getAllClasses();
        Bundle classListActivityBundle = new Bundle();
        classListActivityBundle.putString("CLASS_ID", classes[0].id);
        redirect.putExtras(classListActivityBundle);
        startActivity(redirect);
    }

    private void addClass() {
        Intent redirect = new Intent(this, AddClassActivity.class);
        startActivity(redirect);
    }

    private void editStudents() {
        Intent redirect = new Intent(this, EditStudents.class);
        startActivity(redirect);
    }
}