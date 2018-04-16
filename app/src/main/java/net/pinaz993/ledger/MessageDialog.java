package net.pinaz993.ledger;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.pinaz993.ledger.MainMenu;
import net.pinaz993.ledger.R;

public class MessageDialog extends DialogFragment {
    String message = null;
    String btnText = null;
    View.OnClickListener listener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        message = getArguments().getString("message");
        btnText = getArguments().getString("button text");
        listener = (View.OnClickListener)getArguments().get("listener");
        View view = inflater.inflate(R.layout.message_dialog_layout, container, false);
        Button okButton = view.findViewById(R.id.okBtn);
        if(btnText != null) {
            okButton.setText(btnText);
        } else {
            okButton.setText("Ok");
        }
        if(listener == null) {
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exit();
                }});
        } else {
            okButton.setOnClickListener(listener);
        }

        TextView messageTextView = view.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        return view;
    }

    void exit() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

}
