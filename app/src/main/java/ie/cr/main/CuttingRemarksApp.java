package ie.cr.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.cr.database.DBManager;
import ie.cr.models.Barber;
import ie.cr.database.*;

public class CuttingRemarksApp extends Application
{
    public DBManager dbManager = new DBManager(this);

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