package net.pinaz993.ledger;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
        behaviorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteBehaviorDialog deleteBehaviorDialog = new DeleteBehaviorDialog();
                Bundle bundle = new Bundle();
                bundle.putString("behavior id", ((Behavior)parent.getItemAtPosition(position)).getId());
                deleteBehaviorDialog.setArguments(bundle);
                deleteBehaviorDialog.show(getFragmentManager(), "");
            }
        });

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