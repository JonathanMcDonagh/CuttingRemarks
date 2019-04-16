package ie.cr.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import ie.cr.R;
import ie.cr.fragments.BarberFragment;
import ie.cr.main.CuttingRemarksApp;

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

    //When home icon is clicked
    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    //About alert dialog, explains the app
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


    //Logout button for Facebook Login and FirebaseAuth
    public void menuLogout(MenuItem m){
        Toast.makeText(Base.this,"You've Logged Out", Toast.LENGTH_LONG).show();

        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
        startActivity(new Intent(Base.this, Login.class));

    }
}