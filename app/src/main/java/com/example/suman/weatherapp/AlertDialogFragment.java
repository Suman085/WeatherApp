package com.example.suman.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Suman on 1/9/2017.
 */

public class AlertDialogFragment extends DialogFragment
{
    String error_mess;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       Context context = getActivity();
        error_mess=getTag();
        AlertDialog.Builder builder= new AlertDialog.Builder(context)
                .setTitle(R.string.error_title).setMessage(error_mess).setPositiveButton(R.string.error_okbutton,null);
        AlertDialog dialog=builder.create();
        return dialog;
    }
}
