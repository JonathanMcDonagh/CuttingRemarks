package ie.cr.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import ie.cr.R;

public class SearchFragment extends BarberFragment
        implements AdapterView.OnItemSelectedListener {

    String selected;
    SearchView searchView;

    public SearchFragment() { }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(activity, R.array.barberTypes,
                        android.R.layout.simple_spinner_item);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = activity.findViewById(R.id.searchSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        searchView = activity.findViewById(R.id.searchView);
        searchView.setQueryHint("Search your Barbers");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                barberFilter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                barberFilter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context c) { super.onAttach(c); }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkSelected(String selected)
    {
        if (selected != null) {
            if (selected.equals("All Types")) {
                barberFilter.setFilter("all");
            } else if (selected.equals("Favourites")) {
                barberFilter.setFilter("favourites");
            }

            String filterText = ((SearchView)activity
                    .findViewById(R.id.searchView)).getQuery().toString();

            if(filterText.length() > 0)
                barberFilter.filter(filterText);
            else
                barberFilter.filter("");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void deleteBarbers(ActionMode actionMode) {
        super.deleteBarbers(actionMode);
        checkSelected(selected);
    }

}
