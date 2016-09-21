package de.edlly.bkkstundenplan.bkkstundenplan.gui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import de.edlly.bkkstundenplan.bkkstundenplan.R;


public abstract class AppActivity extends Activity {
    private AlertDialog errorDialog;

    public void showErrorDialog(final String titel, final String msg){
        errorDialog = new AlertDialog.Builder(this).create();
        errorDialog.setTitle(titel);
        errorDialog.setMessage(msg);
        errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                getString(R.string.ok_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        errorDialog.show();
    }
}
