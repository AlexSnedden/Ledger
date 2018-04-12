package net.pinaz993.ledger;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

public class EditBehaviors extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_behaviors);

        ListView behaviorList = findViewById(R.id.behaviorListView);
        ArrayAdapter adapter = new ArrayAdapter<Behavior>(this, R.layout.edit_behaviors_pane_template,
                LedgerDatabase.getDb().getBehaviorDao().getAllBehaviors());
        behaviorList.setAdapter(adapter);
        /*behaviorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Behavior behavior = (Behavior)adapterView.getItemAtPosition(i);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder();
                builder.setMessage("Do you want to delete this behavior?").setPositiveButton("yes", dialogClickListener)
                        .setNegativeButton("no", dialogClickListener).show();

            }
        });*/

        final Button addBehaviorBtn = findViewById(R.id.newBehaviorBtn);
        addBehaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBehavior();
            }
        });
    }
    private void addBehavior() {
        Intent redirect = new Intent(this, AddBehavior.class);
        startActivity(redirect);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenu.class));
    }
}
