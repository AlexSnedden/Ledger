package net.pinaz993.ledger;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DeleteClassDialog extends DialogFragment {
    @Override
    public AlertDialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final Class _class = LedgerDatabase.getDb().getClassDao().
                getClassById(getArguments().getString("class id"));
        alertDialog.setMessage(String.format("Do you want to delete the class \"%s\" and all records of it?", _class.id));
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LedgerDatabase.getDb().getStudentClassMappingDao().deleteMappingsWithClass(_class.id);
                LedgerDatabase.getDb().getBehaviorRecordDao().deleteAllRecordsWithClass(_class.id);
                LedgerDatabase.getDb().getAttendanceDao().deleteRecordsWithClass(_class.id);
                LedgerDatabase.getDb().getClassDao().deleteClass(_class);
                dialogInterface.dismiss();
                startActivity(new Intent(getActivity(), EditClasses.class));
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
