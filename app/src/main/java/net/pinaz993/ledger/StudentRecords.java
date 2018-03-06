package net.pinaz993.ledger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudentRecords extends AppCompatActivity {

    private String STUDENT_ID_KEY = "student_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        extras.getString(STUDENT_ID_KEY, null);


    }
}
