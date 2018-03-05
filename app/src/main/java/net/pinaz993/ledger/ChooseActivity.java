package net.pinaz993.ledger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseActivity extends AppCompatActivity {
    Button addStudentButton, addClassButton, gotoClassListButton;

    Spinner classSpinner;

    String chosenClassID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        addStudentButton = findViewById(R.id.add_student_btn);
        addClassButton = findViewById(R.id.add_class_btn);
        gotoClassListButton = findViewById(R.id.goto_class_list_btn);

        classSpinner = findViewById(R.id.class_spinner);

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(v.getContext(), AddStudentActivity.class);
                startActivity(redirect);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(v.getContext(), AddClassActivity.class);
                startActivity(redirect);
            }
        });

        gotoClassListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenClassID != null) {
                    Intent redirect = new Intent(v.getContext(), ClassListActivity.class);
                    redirect.putExtra("CLASS_ID", chosenClassID); // Tell the activity what class to load up
                    startActivity(redirect);

                }
            }
        });

        classSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getAllClasses()));

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenClassID = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chosenClassID = null;
            }
        });
    }

    String[] getAllClasses() {
        //TODO: Implement getAllClasses for Room database
        return new String[]{};
    }


}
