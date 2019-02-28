package ie.cr.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ie.cr.R;
import ie.cr.activities.Base;
import ie.cr.adapters.BarberFilter;
import ie.cr.adapters.BarberListAdapter;
import ie.cr.models.Barber;

public class BarberFragment  extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static BarberListAdapter listAdapter;
    public ListView listView;
    public BarberFilter barberFilter;
    public boolean favourites = false;

    public BarberFragment() {
        //Required Empty Constructor
    }

    //If barber is clicked allows the user to edit the barber rating
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("barberId", (String) view.getTag());

        Fragment fragment = EditFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editBarberLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Barber Fragment instance
    public static BarberFragment newInstance() {
        BarberFragment fragment = new BarberFragment();
        return fragment;
    }

    //adds the fragment to the activity
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    // Inflates the barber fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        getActivity().setTitle(R.string.recentlyViewedLbl);
        listAdapter = new BarberListAdapter(activity, this, activity.app.dbManager.getAll());
        barberFilter = new BarberFilter(activity.app.dbManager.getAll(),"all",listAdapter);

        if (favourites) {
            getActivity().setTitle(R.string.favouritesBarberLbl);
            barberFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            barberFilter.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        // setRandomBarber();

        listView = v.findViewById(R.id.homeList);

        setListView(v);

        return v;
    }

    //Sets list view for barbers
    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
    }

    //Allows the activity to become visible to the user
    @Override
    public void onStart()
    {
        super.onStart();
    }


    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Barber)
        {
            onBarberDelete ((Barber) view.getTag());
        }
    }

    //Allows the user to delete a barber but gives a alert dialog first to make sure they
    // want to delete that barber
    public void onBarberDelete(final Barber barber)
    {
        String stringName = barber.barberName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Barber\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.dbManager.delete(barber.barberId); // remove from our list
                listAdapter.barberList.remove(barber); // update adapters data
                listAdapter.notifyDataSetChanged(); // refresh adapter
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_barber:
                deleteBarbers(actionMode);
                return true;
            default:
                return false;
        }
    }

    //Deletes the barber from the SQLite database
    public void deleteBarbers(ActionMode actionMode)
    {
        Barber c = null;
        for (int i = listAdapter.getCount() - 1; i >= 0; i--) {
            if (listView.isItemChecked(i)) {
                activity.app.dbManager.delete(listAdapter.getItem(i).barberId); //delete from DB
                listAdapter.barberList.remove(listAdapter.getItem(i)); // update adapters data
            }
        }

        actionMode.finish();

        if (favourites) {
            //Update the filters data
            barberFilter = new BarberFilter(activity.app.dbManager.getAll(),"favourites",listAdapter);
            barberFilter.filter(null);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */
}