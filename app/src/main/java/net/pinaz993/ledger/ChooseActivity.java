package net.pinaz993.ledger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseActivity extends AppCompatActivity {
    Button addStudentButton, addClassButton, gotoClassListButton;

    Spinner classSpinner = findViewById(R.id.class_spinner);

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
    }


}
