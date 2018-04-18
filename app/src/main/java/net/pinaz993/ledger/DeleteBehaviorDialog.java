package net.pinaz993.ledger;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DeleteBehaviorDialog extends DialogFragment {
    @Override
    public AlertDialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final Behavior behavior = LedgerDatabase.getDb().getBehaviorDao().
                getBehaviorById(getArguments().getString("behavior id"));
        alertDialog.setMessage(String.format("Do you want to delete the \"%s\" behavior and all records of it?", behavior.getName()));
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LedgerDatabase.getDb().getBehaviorRecordDao().deleteAllRecordsWithBehavior(behavior.getId());
                LedgerDatabase.getDb().getBehaviorDao().deleteBehavior(behavior);
                dialogInterface.dismiss();
                startActivity(new Intent(getActivity(), EditBehaviors.class));
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return alertDialog.create();

    }
}
