package ie.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ie.cr.R;
import ie.cr.fragments.BarberFragment;
import ie.cr.models.Barber;

public class Home extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(app.barberList.isEmpty()) setupBarbers();
    }

    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    public void search(View v) {
        startActivity(new Intent(this, Search.class));
    }

    public void favourites(View v) { startActivity(new Intent(this, Favourites.class)); }

    @Override
    protected void onResume() {
        super.onResume();

        barberFragment = BarberFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, barberFragment)
                .commit();
    }

    public void setupBarbers(){
        app.barberList.add(new Barber("Lauren", "The Bearded Lady",5,10,false));
        app.barberList.add(new Barber("Johnny", "Portland Barbers",3.5,12,true));
        app.barberList.add(new Barber("Jill", "Chapz",2.5,15,true));
        app.barberList.add(new Barber("James", "Bladez",2.5,20,true));
        app.barberList.add(new Barber("Tom", "Chapz",2.5,15,false));
        app.barberList.add(new Barber("Mike", "Bladez",2.5,20,true));
    }

}

