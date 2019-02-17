package ie.cr.activities;

import android.os.Bundle;

import ie.cr.R;
import ie.cr.fragments.SearchFragment;

public class Search extends Base  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barberFragment = SearchFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, barberFragment)
                .commit();
    }
}
