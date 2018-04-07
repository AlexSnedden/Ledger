package net.pinaz993.ledger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * A dialog box that allows the user to select mutliple behaviors and record them for a single student.
 * It uses a custom layout that segments the behaviors into positive, negative and neutral lists,
 * each with unique formatting. A list will set itself as invisible if it has no options to present.
 * When the OK button is pressed, each behavior is timestamped and recorded in the database. The
 * cancel button closes the dialog box without doing anything.
 * Created by Patrick Shannon on 11/14/2017.
 */

public class BehaviorDialog extends DialogFragment {
    private View content;
    Student student;
    String classId;
    ListView positiveBehaviorList, neutralBehaviorList, negativeBehaviorList;


    public interface BehaviorDialogListener {
        void onDialogPositiveClick(BehaviorDialog dialog);
    }

    @SuppressLint("InflateParams")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        content = inflater.inflate(R.layout.behavior_box_list_template, null);
        Bundle args = getArguments();
        student = LedgerDatabase.getDb().getStudentDao().getStudent(args.getString("studentId"));
        classId = getArguments().getString("classId");
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.behavior_box_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BehaviorDialog.this.getDialog().cancel();
            }
        });

        builder.setView(content);
        positiveBehaviorList = content.findViewById(R.id.behaviors_positive);
        neutralBehaviorList = content.findViewById(R.id.behaviors_neutral);
        negativeBehaviorList = content.findViewById(R.id.behaviors_negative);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox behaviorCheckbox = view.findViewById(R.id.behavior_selected_checkbox);
                if(behaviorCheckbox.isChecked()) {
                    behaviorCheckbox.setChecked(false);
                } else {
                    behaviorCheckbox.setChecked(true);
                }
            }
        };

        positiveBehaviorList.setOnItemClickListener(listener);
        negativeBehaviorList.setOnItemClickListener(listener);
        neutralBehaviorList.setOnItemClickListener(listener);

        positiveBehaviorList.setAdapter(
                new BehaviorAdapter(getActivity(), 1, positiveBehaviorList, student, classId));
        neutralBehaviorList.setAdapter(
                new BehaviorAdapter(getActivity(), 0, neutralBehaviorList, student, classId));
        negativeBehaviorList.setAdapter(
                new BehaviorAdapter(getActivity(), -1, negativeBehaviorList, student, classId));

        return builder.create();
    }

}

class BehaviorAdapter extends ArrayAdapter{
    Context context;
    Student student;
    String classId;

    public static Behavior[] collateBehaviorsByPositivity(int pos, Context context) {
        Behavior[] behaviors = LedgerDatabase.getDb().getBehaviorDao().getAllBehaviors();
        ArrayList<Behavior> rtn = new ArrayList<>();
        for (Behavior behavior : behaviors) {
            if (behavior.positivity == pos) rtn.add(behavior);
        }
        return rtn.toArray(new Behavior[]{});
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     */
    public BehaviorAdapter(@NonNull Context context, int positivity, ViewGroup parent, Student student, String classId) {
        super(context, R.layout.behavior_box_row_item, collateBehaviorsByPositivity(positivity, context));
        this.context = context;
        if(getCount() == 0) parent.setVisibility(View.INVISIBLE);
        this.student = student;
        this.classId = classId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        final View result;


        holder = new ViewHolder();
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.behavior_box_row_item, parent, false);

        holder.behaviorNameTxt = convertView.findViewById(R.id.behavior_name_txt);
        holder.behaviorSelectedCheckbox = convertView.findViewById(R.id.behavior_selected_checkbox);

        result = convertView;
        convertView.setTag(holder);

        final Behavior behavior = (Behavior) getItem(position);
        holder.behaviorNameTxt.setText(behavior.name);

        DateTime dateTime = new DateTime();
        final String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        // check if a behavior record was recorded today
        BehaviorRecord existingRecord = LedgerDatabase.getDb().getBehaviorRecordDao().getBehaviorRecord(
                                        student.getId(), behavior.getId(), classId, currentDate);
        if(existingRecord != null) {
            holder.behaviorSelectedCheckbox.setChecked(true);
        } else {
            holder.behaviorSelectedCheckbox.setChecked(false);
        }
        holder.behaviorSelectedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                BehaviorRecord behaviorRecord = new BehaviorRecord();
                behaviorRecord.setClassId(classId);
                behaviorRecord.setStudentId(student.getId());
                behaviorRecord.setBehaviorId(behavior.getId());
                behaviorRecord.setDate(currentDate);
                if(LedgerDatabase.getDb().getBehaviorRecordDao()
                        .getBehaviorRecord(student.getId(), behavior.getId(), classId, currentDate) != null) {
                    // record has been recorded. Uncheck the button and delete the record
                    LedgerDatabase.getDb().getBehaviorRecordDao().deleteBehaviorRecord(behaviorRecord);
                } else {
                    // record has not been recorded. Check the button and add the record.
                    LedgerDatabase.getDb().getBehaviorRecordDao().recordBehaviorRecord(behaviorRecord);
                }
            }
        });
        return result;
    }

    private class ViewHolder{
        CheckBox behaviorSelectedCheckbox;
        TextView behaviorNameTxt;
    }
}
