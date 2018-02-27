package net.pinaz993.ledger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by A.J. on 2/27/18.
 */

public class AddStudentsToClassPrompt extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Test");

    }
}
