package ie.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ie.cr.R;
import ie.cr.fragments.BarberFragment;
import ie.cr.models.Barber;

public class Home extends Base {

    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyList = findViewById(R.id.emptyList);

        if(barberList.isEmpty()) setupBarbers();
    }

    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(barberList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl));
        else
            emptyList.setText("");

        barberFragment = BarberFragment.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, barberFragment)
                .commit();

    }

    public void setupBarbers(){
        barberList.add(new Barber("Lauren", "The Bearded Lady",5,10,false));
        barberList.add(new Barber("Johnny", "Portland Barbers",3.5,12,true));
        barberList.add(new Barber("Jill", "Chapz",2.5,15,true));
    }

}

