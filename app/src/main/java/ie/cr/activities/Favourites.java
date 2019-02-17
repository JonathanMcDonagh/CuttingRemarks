package ie.cr.activities;

import android.os.Bundle;
import android.widget.TextView;

import ie.cr.R;
import ie.cr.fragments.BarberFragment;

public class Favourites extends Base {

    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        emptyList = findViewById(R.id.emptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(app.barberList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl));
        else
            emptyList.setText("");

        barberFragment = BarberFragment.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, barberFragment)
                .commit();
    }
}
