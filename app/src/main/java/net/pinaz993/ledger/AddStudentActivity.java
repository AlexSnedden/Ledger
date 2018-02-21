package net.pinaz993.ledger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        EditText studentId = (EditText)findViewById(R.id.studentIdInput);
        EditText firstName = (EditText)findViewById(R.id.firstNameInput);
        EditText lastName = (EditText)findViewById(R.id.lastNameInput);
        EditText email = (EditText)findViewById(R.id.studentEmailInput);

        Button addStudentButton = (Button)findViewById(R.id.addStudentButton);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
