package net.pinaz993.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ClassListActivity extends AppCompatActivity implements
        ExpandableLayout.OnExpansionUpdateListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private String classID;
    private SettingsHandler settings = SettingsHandler.getInstance();
    private ExpandableLayout optionContainer;
    private Button dropMenuButton;
    private String CLASS_ID_KEY;
    private String[] classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        CLASS_ID_KEY = getString(R.string.class_id_key);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        classID = (extras != null) ? extras.getString(CLASS_ID_KEY) : null;
        if (classID == null) {
            // activate the class selector dialog, use it to return a valid classID
        } else {
            getSupportActionBar().setTitle(classID);
        }

        optionContainer = (ExpandableLayout) findViewById(R.id.option_container);
        optionContainer.setOnExpansionUpdateListener(this);

        ListView studentList = (ListView) findViewById(R.id.student_list);
        Student[] students = getStudentsInClass();
        StudentPaneAdapter studentPaneAdapter = new StudentPaneAdapter(this, students);
        studentList.setAdapter(studentPaneAdapter);


        classes = getAllClasses();
        ListView optionsList = (ListView) findViewById(R.id.options_list);
        optionsList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, classes));
        optionsList.setOnItemClickListener(this);

        settings.setLastActivityRun(SettingsHandler.ACTIVITY.CLASS_LIST);
        settings.setLastClassID(classID);
    }

    //<editor-fold desc="Get Stuff From Database">
    private String[] getAllClasses(){
        //TODO: Implement getAllClasses() for Room database
        return null;
    }

    private Student[] getStudentsInClass(){
        //TODO: Implement getStudentsInClasses() for Room database
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Listener Callbacks">
    @Override
    public void onExpansionUpdate(float expansionFraction, int state){
        dropMenuButton.setRotation((int)(expansionFraction * 180));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.class_list_menu, menu);
        MenuItem actionList = menu.findItem(R.id.action_list);
        actionList.setActionView(R.layout.arrow);
        dropMenuButton = (Button) actionList.getActionView();
        dropMenuButton.setOnClickListener(this);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent goTo = new Intent(this, ClassListActivity.class);
        goTo.putExtra(CLASS_ID_KEY, classes[position]);
        startActivity(goTo);

    }

    @Override
    public void onClick(View v) {
        optionContainer.toggle(true);
    }
    //</editor-fold>
}
