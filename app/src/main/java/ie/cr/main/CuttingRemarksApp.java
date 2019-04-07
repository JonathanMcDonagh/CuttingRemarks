package ie.cr.main;

import android.app.Application;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import ie.cr.database.DBManager;
import ie.cr.models.Barber;
import ie.cr.database.*;

public class CuttingRemarksApp extends Application
{
    public DBManager dbManager = new DBManager(this);
    private static CuttingRemarksApp mInstance;
    public static final String TAG = CuttingRemarksApp.class.getName();

    /* Client used to interact with Google APIs. */
    public GoogleApiClient mGoogleApiClient;;


    public Location mCurrentLocation;


    public static synchronized CuttingRemarksApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Cutting Remarks", "Cutting Remarks App Started");
        dbManager.open();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        dbManager.close();
    }
}