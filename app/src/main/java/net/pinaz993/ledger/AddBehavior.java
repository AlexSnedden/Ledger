package net.pinaz993.ledger;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class AddBehavior extends AppCompatActivity {
    ToggleButton positiveBtn;
    ToggleButton neutralBtn;
    ToggleButton negativeBtn;
    EditText behaviorName;
    EditText behaviorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_behavior);

        positiveBtn = findViewById(R.id.positiveBtn);
        neutralBtn = findViewById(R.id.neutralBtn);
        negativeBtn = findViewById(R.id.negativeBtn);
        behaviorName = findViewById(R.id.behaviorNameInput);
        Button addBehaviorBtn = findViewById(R.id.addBehaviorButton);
        neutralBtn.setChecked(true);

        positiveBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    neutralBtn.setChecked(false);
                    negativeBtn.setChecked(false);
                }
            }
        });
        neutralBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    positiveBtn.setChecked(false);
                    negativeBtn.setChecked(false);
                }
            }
        });
        negativeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    positiveBtn.setChecked(false);
                    neutralBtn.setChecked(false);
                }
            }
        });

        addBehaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Behavior behavior = new Behavior();
                behavior.setName(behaviorName.getText().toString());
                behavior.setId(ObjectIds.getLatestBehaviorId());
                ObjectIds.updateBehaviorId();
                if(!neutralBtn.isChecked()) {
                    if(!positiveBtn.isChecked()) {
                        behavior.setPositivity(-1);
                    } else {
                        behavior.setPositivity(1);
                    }
                }
                addBehavior(behavior);
            }
        });
    }

    public void addBehavior(Behavior behavior) {
        MessageDialog messageDialog = new MessageDialog();
        Bundle bundle = new Bundle();
        try {
            LedgerDatabase.getDb().getBehaviorDao().addBehavior(behavior);
            bundle.putString("message", "Behavior added!");
            behaviorName.setText("");
            positiveBtn.setChecked(false);
            neutralBtn.setChecked(false);
            negativeBtn.setChecked(false);
        } catch(Exception e) {
            bundle.putString("message", "Failed to add behavior");
        }
        messageDialog.show(getFragmentManager(), "");
        messageDialog.setArguments(bundle);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EditBehaviors.class));
    }

    public void goHome(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }

}
