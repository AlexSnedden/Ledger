package net.pinaz993.ledger;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

public class EditStudentsClassesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Student student = LedgerDatabase.getDb().getStudentDao().getStudent(getIntent()
                                                                            .getStringExtra("STUDENT_ID"));
        getSupportActionBar().setTitle(String.format("Edit their classes", student.getFirstName()));
        TextView studentLabel = findViewById(R.id.studentLabel);
        studentLabel.setText(String.format("Check all classes %s is in", student.getFirstName()));

        ListView classView = findViewById(R.id.editClassesListView);

        ArrayList<StudentClassMapping> mappings = new ArrayList<>();
        Class[] classes = LedgerDatabase.getDb().getClassDao().getAllClasses();
        StudentClassMapping map;
        for(Class _class: classes) {
            map = new StudentClassMapping();
            Log.v("ClassTag", _class.id);
            map.setClassId(_class.id);
            map.setStudentId(student.getId());
            mappings.add(map);
        }
        ArrayAdapter adapter = new CheckmarkClassAdapter(getApplicationContext(),
                                                       mappings.toArray());
        classView.setAdapter(adapter);
        classView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        classView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudentClassMapping mapping = (StudentClassMapping)adapterView.getItemAtPosition(i);
                CheckBox checkbox = (CheckBox) view;
                if(!checkbox.isChecked()) {
                    // student was added to class
                    try {
                        LedgerDatabase.getDb().getStudentClassMappingDao().addStudentClassMapping(mapping);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    checkbox.setChecked(true);
                } else {
                    // student was removed from class
                    LedgerDatabase.getDb().getStudentClassMappingDao().deleteStudentClassMapping(mapping);
                    checkbox.setChecked(false);
                }
            }
        });

    }

    private class CheckmarkClassAdapter extends ArrayAdapter {

        public CheckmarkClassAdapter(Context context, Object[] objects) {
            super(context, R.layout.edit_students_classes_pane_template, objects);
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            CheckBox checkbox = new CheckBox(getContext());
            StudentClassMapping mapping = (StudentClassMapping)getItem(position);
            checkbox.setText(mapping.getClassId());
            checkbox.setClickable(false);
            checkbox.setFocusable(false);
            checkbox.setTextColor(Color.BLACK);
            // check if student is in class. If so, set the check on
            if(LedgerDatabase.getDb().getStudentClassMappingDao().getStudentClassMapping(
                    mapping.getStudentId(), mapping.getClassId()) != null) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }

            return checkbox;
        }

    }

}
