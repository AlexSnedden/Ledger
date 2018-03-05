package net.pinaz993.ledger;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);

        final EditText className = findViewById(R.id.className);
        final Button addClassButton = findViewById(R.id.addClassButton);
        final Class newClass = new Class();
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newClass.setId(className.getText().toString());
                try {
                    LedgerDatabase.getDb().getClassDao().addClass(newClass);
                    /*Snackbar successMessage = Snackbar.make(coordinatorLayout, "Successfully added class!", 5000);
                    successMessage.show();*/
                    className.setText("");
                    showDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar failureMessage = Snackbar.make(coordinatorLayout, "Failed to add class.", 5000);
                    failureMessage.show();
                }
            }

        });
    }
    void showDialog() {
        DialogFragment newFragment = AddStudentsToClassPrompt.newInstance();
        newFragment.show(getFragmentManager(), "dialog");
    }

}

