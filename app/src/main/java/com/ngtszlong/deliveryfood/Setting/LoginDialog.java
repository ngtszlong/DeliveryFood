package com.ngtszlong.deliveryfood.Setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ngtszlong.deliveryfood.R;

public class LoginDialog extends AppCompatDialogFragment {

    private EditText edt_email;
    private EditText edt_password;
    private LoginDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.logindialog, null);
        builder.setView(view).setTitle(R.string.login).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    edt_email.setError(getText(R.string.input_your_username));
                } else if (TextUtils.isEmpty(password)) {
                    edt_password.setError(getText(R.string.input_your_password));
                } else {
                    listener.applyText(username, password);
                }
            }
        });

        edt_email = view.findViewById(R.id.edt_login_email);
        edt_password = view.findViewById(R.id.edt_login_password);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (LoginDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LoginDialogListener");
        }
    }

    public interface LoginDialogListener {
        void applyText(String email, String password);
    }
}
