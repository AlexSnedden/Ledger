package net.pinaz993.ledger;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class DeleteStudentDialog extends DialogFragment {
    @Override
    public AlertDialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final Student student = LedgerDatabase.getDb().getStudentDao().
                getStudent(getArguments().getString("student id"));
        alertDialog.setMessage(String.format("Do you want to delete the student \"%s %s\" and all records of their\'s?",
                                                                            student.getFirstName(), student.getLastName()));
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LedgerDatabase.getDb().getBehaviorRecordDao().deleteAllRecordsWithStudent(student.getId());
                LedgerDatabase.getDb().getAttendanceDao().deleteRecordsWithStudent(student.getId());
                LedgerDatabase.getDb().getStudentClassMappingDao().deleteMappingsWithStudent(student.getId());
                LedgerDatabase.getDb().getStudentDao().deleteStudent(student);

                dialogInterface.dismiss();
                startActivity(new Intent(getActivity(), EditStudents.class));
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
