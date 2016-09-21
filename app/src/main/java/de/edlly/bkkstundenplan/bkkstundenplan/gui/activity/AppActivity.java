package de.edlly.bkkstundenplan.bkkstundenplan.gui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

import de.edlly.bkkstundenplan.bkkstundenplan.R;


public abstract class AppActivity extends Activity {
    private static final String PREFS_DATA = "prefData";
    private static final String PREFS_FAV_CLASS_NAME = "prefClassName";

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

    protected void setClassFav(String className) {
        SharedPreferences settings = getSharedPreferences(PREFS_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_FAV_CLASS_NAME, className);

        editor.apply();

        Toast.makeText(this, "Klasse: " + className + " als favorit gespeichert", Toast.LENGTH_SHORT).show();
    }

    protected String getClassFav(){
        SharedPreferences settings = getSharedPreferences(PREFS_DATA, 0);
        return settings.getString(PREFS_FAV_CLASS_NAME, null);
    }
}
