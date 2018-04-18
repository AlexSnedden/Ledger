package net.pinaz993.ledger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.net.Proxy.Type.HTTP;

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
        Button editBehaviorsBtn = findViewById(R.id.editBehaviorsButton);
        Button exportDataBtn = findViewById(R.id.exportDataBtn);

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
        editBehaviorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBehaviors();
            }
        });
        exportDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    exportData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void takeAttendance() {
        Class classes[] = LedgerDatabase.getDb().getClassDao().getAllClasses();
        if(classes.length == 0) {
            MessageDialog messageDialog = new MessageDialog();
            Bundle bundle = new Bundle();
            bundle.putString("message", "No classes have been added yet!");
            bundle.putString("button text", "Ok");
            messageDialog.setArguments(bundle);
            messageDialog.show(getFragmentManager(), "No classes");
        } else {
            Intent redirect = new Intent(this, ClassListActivity.class);
            Bundle classListActivityBundle = new Bundle();
            classListActivityBundle.putString("CLASS_ID", classes[0].id);
            redirect.putExtras(classListActivityBundle);
            startActivity(redirect);
        }
    }

    private void addClass() {
        Intent redirect = new Intent(this, AddClassActivity.class);
        startActivity(redirect);
    }

    private void editStudents() {
        Intent redirect = new Intent(this, EditStudents.class);
        startActivity(redirect);
    }

    private void editBehaviors() {
        Intent redirect = new Intent(this, EditBehaviors.class);
        startActivity(redirect);
    }

    private void exportData() throws Exception {
        /*
        Emails two files.
        Attendance File: Date, Class, First and Last name, Present/Absent, Left Early/On Time, Arrived Late/On Time,
                         Excused/Not Excused

        Behavior File: Date, Class, First and Last name, Behavior, Pos/Neg/Neutral
         */
        File attendanceFile = new File(getExternalFilesDir(null), "attendance_data.csv");
        File behaviorFile = new File(getExternalFilesDir(null), "behavior_data.csv");
        attendanceFile.setReadable(true);
        behaviorFile.setReadable(true);

        OutputStream attendanceFileOutputStream = new FileOutputStream(attendanceFile, false);
        OutputStream behaviorFileOutputStream = new FileOutputStream(behaviorFile, false);

        String attendanceFileString = "", behaviorFileString = "";
        attendanceFileString += "Date,Class,Name,Present/Absent,Left Early/On Time,Arrived Late/On Time,Excused\n";
        behaviorFileString += "Date,Class,Name,Behavior,Pos/Neg/Neutral\n";
        Class[] classes = LedgerDatabase.getDb().getClassDao().getAllClasses();
        Attendance[] attendances;
        for(Class _class: classes) {
            attendances = LedgerDatabase.getDb().getAttendanceDao().getRecordsForClass(_class.toString());
            for(Attendance attendance: attendances) {
                attendanceFileString += String.format("%s,%s,%s,%s,%s,%s,%s\n",
                        attendance.getDate(),
                        LedgerDatabase.getDb().getClassDao().getClassById(attendance.getClassId()).toString(),
                        LedgerDatabase.getDb().getStudentDao().getStudent(attendance.getStudentId()),
                        (attendance.present) ? "present" : "absent",
                        (attendance.earlyDeparture) ? "departed early" : "departed on time",
                        (attendance.lateArrival) ? "arrived late" : "arrived on time",
                        (attendance.excused) ? "excused" : "");
               }
        }
        Student[] students = LedgerDatabase.getDb().getStudentDao().getAllStudents();
        for(Student student: students) {
            BehaviorRecord[] behaviorRecords = LedgerDatabase.getDb().getBehaviorRecordDao().getAllBehaviorRecords();
            for(BehaviorRecord behaviorRecord: behaviorRecords) {
                Behavior behavior = LedgerDatabase.getDb().getBehaviorDao().getBehaviorById(behaviorRecord.getBehaviorId());
                String positivity = null;
                switch(behavior.getPositivity()) {
                    case 1: positivity = "Positive";
                            break;
                    case 0: positivity = "Neutral";
                            break;
                    case -1: positivity = "Negative";
                            break;
                }
                behaviorFileString += String.format("%s,%s,%s,%s,%s\n",
                        behaviorRecord.getDate(),
                        LedgerDatabase.getDb().getClassDao().getClassById(behaviorRecord.getClassId()).id,
                        student.getFirstName() + student.getLastName(),
                        behavior.getName(),
                        positivity);
            }
        }

        attendanceFileOutputStream.write(attendanceFileString.getBytes());
        behaviorFileOutputStream.write(behaviorFileString.getBytes());

        ArrayList<Uri> files = new ArrayList<>();
        files.add(Uri.fromFile(attendanceFile));
        files.add(Uri.fromFile(behaviorFile));
        attendanceFileOutputStream.close();
        behaviorFileOutputStream.close();
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("message/rfc822");
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ledger Data Export");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is an email from Ledger");
        startActivity(Intent.createChooser(emailIntent, "send email"));
    }

    @Override
    public void onBackPressed() {}
}