package ie.cr.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.cr.models.Barber;

public class CuttingRemarksApp extends Application
{
    public List <Barber> barberList = new ArrayList<>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("barbermate", "BarberMate App Started");
    }
}