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

public class EditClasses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classes);

        ListView classList = findViewById(R.id.classListView);
        // uses behavior pane template, but this works so let's leave it for now...
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.edit_behaviors_pane_template,
                LedgerDatabase.getDb().getClassDao().getAllClasses());
        classList.setAdapter(adapter);
        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MyTag", "I was called");
                DeleteClassDialog deleteClassDialog = new DeleteClassDialog();
                Bundle bundle = new Bundle();
                bundle.putString("class id", ((Class)parent.getItemAtPosition(position)).id);
                deleteClassDialog.setArguments(bundle);
                deleteClassDialog.show(getFragmentManager(), "");
            }
        });

        final Button addClassBtn = findViewById(R.id.newClassBtn);
        addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass();
            }
        });

        MessageDialog messageDialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", "Press and hold a class to delete it.");
        messageDialog.setArguments(bundle);
        messageDialog.show(getFragmentManager(), "");
    }
    private void addClass() {
        Intent redirect = new Intent(this, AddClassActivity.class);
        startActivity(redirect);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenu.class));
    }

}