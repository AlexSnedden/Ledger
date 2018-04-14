package net.pinaz993.ledger;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
        final EditText studentId = (EditText)findViewById(R.id.studentIdInput);
        final EditText firstName = (EditText)findViewById(R.id.firstNameInput);
        final EditText lastName = (EditText)findViewById(R.id.lastNameInput);
        final EditText email = (EditText)findViewById(R.id.studentEmailInput);

        Button addStudentButton = (Button)findViewById(R.id.addStudentButton);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setId(studentId.getText().toString());
                student.setFirstName(firstName.getText().toString());
                student.setLastName(lastName.getText().toString());
                student.setEmailAddress(email.getText().toString());

                MessageDialog messageDialog = new MessageDialog();

                try {
                    LedgerDatabase.getDb().getStudentDao().addStudent(student);
                    goBack();
                } catch (Exception e) {

                }
            }
        });
    }

    public void goBack() {
        startActivity(new Intent(this, EditStudents.class));
    }
}
