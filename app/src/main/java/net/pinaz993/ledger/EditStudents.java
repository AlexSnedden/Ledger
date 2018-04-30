package net.pinaz993.ledger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EditStudents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);

        Button newStudentBtn = findViewById(R.id.newStudentBtn);
        newStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewStudent();
            }
        });

        ListView studentList = findViewById(R.id.studentListView);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.edit_students_pane_template, LedgerDatabase.getDb().getStudentDao().getAllStudents());
        studentList.setAdapter(adapter);
        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = (Student)adapterView.getItemAtPosition(i);
                editStudentsClasses(student.getId());
            }
        });
        studentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("student id", ((Student)adapterView.getItemAtPosition(i)).getId());
                DeleteStudentDialog deleteStudentDialog = new DeleteStudentDialog();
                deleteStudentDialog.setArguments(bundle);
                deleteStudentDialog.show(getFragmentManager(), "");
                return true;
            }
        });
        MessageDialog messageDialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", "Press a student to modify their classes. Press and hold a student to delete them.");
        messageDialog.setArguments(bundle);
        messageDialog.show(getFragmentManager(), "");
    }

    private void addNewStudent() {
        startActivity(new Intent(this, AddStudentActivity.class));
    }

    private void editStudentsClasses(String studentId) {
        Intent redirect = new Intent(this, EditStudentsClassesActivity.class);
        redirect.putExtra("STUDENT_ID", studentId);
        startActivity(redirect);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenu.class));
    }

    public void goHome(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }
}
