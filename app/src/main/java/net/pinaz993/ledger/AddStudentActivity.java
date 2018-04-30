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
        final EditText firstName = (EditText)findViewById(R.id.firstNameInput);
        final EditText lastName = (EditText)findViewById(R.id.lastNameInput);
        final EditText email = (EditText)findViewById(R.id.studentEmailInput);

        Button addStudentButton = (Button)findViewById(R.id.addStudentButton);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setId(ObjectIds.getLatestStudentId());
                ObjectIds.updateStudentId();
                student.setFirstName(firstName.getText().toString());
                student.setLastName(lastName.getText().toString());
                student.setEmailAddress(email.getText().toString());

                MessageDialog messageDialog = new MessageDialog();
                Bundle bundle = new Bundle();
                try {
                    LedgerDatabase.getDb().getStudentDao().addStudent(student);
                    bundle.putString("message", "Added student!");
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                } catch (Exception e) {
                    bundle.putString("message", "Failed to add student.");
                }
                messageDialog.setArguments(bundle);
                messageDialog.show(getFragmentManager(), "");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EditStudents.class));
    }

    public void goHome(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }
}
