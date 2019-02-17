package ie.cr.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ie.cr.R;
import ie.cr.fragments.BarberFragment;
import ie.cr.main.CuttingRemarksApp;
import ie.cr.models.Barber;

public class Base extends AppCompatActivity {

    public CuttingRemarksApp app;
    public Bundle activityInfo;
    public BarberFragment barberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CuttingRemarksApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void menuHelp(MenuItem m) {
        startActivity(new Intent(this, Help.class));
    }
}
