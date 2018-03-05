package net.pinaz993.ledger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Takes a bundle loaded with the name of the class. Leads to new activity to add students to this class.
 * Created by A.J. on 2/27/18.
 */

public class AddStudentsToClassPrompt extends DialogFragment {
    public static AddStudentsToClassPrompt newInstance(/*int title*/) {
        AddStudentsToClassPrompt frag = new AddStudentsToClassPrompt();
        Bundle args = new Bundle();
        //args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent redirect = new Intent(getActivity(), AddStudentActivity.class);
                startActivity(redirect);
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int whichButton) {
               //
           }
        });
        return builder.create();
    }
}
