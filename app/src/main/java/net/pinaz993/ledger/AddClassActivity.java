package net.pinaz993.ledger;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
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
                MessageDialog messageDialog = new MessageDialog();
                Bundle bundle = new Bundle();
                try {
                    LedgerDatabase.getDb().getClassDao().addClass(newClass);
                    bundle.putString("message", "Added class!");
                    className.setText("");
                } catch (Exception e) {
                    bundle.putString("message", "Failed to add class.");
                }
                messageDialog.setArguments(bundle);
                messageDialog.show(getFragmentManager(), "");
            }

        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EditClasses.class));
    }

    public void goHome(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }
}

