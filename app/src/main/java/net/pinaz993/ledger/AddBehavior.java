package net.pinaz993.ledger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class AddBehavior extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_behavior);

        final ToggleButton positiveBtn = findViewById(R.id.positiveBtn);
        final ToggleButton neutralBtn = findViewById(R.id.neutralBtn);
        final ToggleButton negativeBtn = findViewById(R.id.negativeBtn);
        final EditText behaviorName = findViewById(R.id.behaviorNameInput);
        final EditText behaviorId = findViewById(R.id.behaviorIdInput);
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
                behavior.setId(behaviorId.getText().toString());
                if(!neutralBtn.isChecked()) {
                    if(!positiveBtn.isChecked()) {
                        behavior.setPositivity(-1);
                    } else {
                        behavior.setPositivity(1);
                    }
                }
                addBehavior(behavior);
                goBack();
            }
        });
    }

    public void addBehavior(Behavior behavior) {
        LedgerDatabase.getDb().getBehaviorDao().addBehavior(behavior);
    }

    public void goBack() {
        startActivity(new Intent(this, EditBehaviors.class));
    }


}
