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
        // Required empty public constructor
    }

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


    public static BarberFragment newInstance() {
        BarberFragment fragment = new BarberFragment();
        return fragment;
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);
        getActivity().setTitle(R.string.recentlyViewedLbl);
        listAdapter = new BarberListAdapter(activity, this, activity.app.barberList);
        barberFilter = new BarberFilter(activity.app.barberList,"all",listAdapter);

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

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
    }

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
                activity.app.barberList.remove(barber); // remove from our list
                listAdapter.barberList.remove(barber); // update adapters data
                setRandomBarber();
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

    public void deleteBarbers(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.barberList.remove(listAdapter.getItem(i));
                if (favourites)
                   listAdapter.barberList.remove(listAdapter.getItem(i));
            }
        }
        setRandomBarber();
        listAdapter.notifyDataSetChanged(); // refresh adapter

        actionMode.finish();
    }

    public void setRandomBarber() {

        ArrayList<Barber> barberList = new ArrayList<>();

        for(Barber c : activity.app.barberList)
            if (c.favourite)
                barberList.add(c);

        if (favourites)
            if( !barberList.isEmpty()) {
                Barber randomBarber = barberList.get(new Random()
                            .nextInt(barberList.size()));

                /*((TextView) getActivity().findViewById(R.id.favouriteBarberName)).setText(randomBarber.barberName);
                ((TextView) getActivity().findViewById(R.id.favouriteBarberShop)).setText(randomBarber.shop);
                ((TextView) getActivity().findViewById(R.id.favouriteBarberPrice)).setText("€ " + randomBarber.price);
                ((TextView) getActivity().findViewById(R.id.favouriteBarberRating)).setText(randomBarber.rating + " *");*/
            }
            else {
                /*((TextView) getActivity().findViewById(R.id.favouriteBarberName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBarberShop)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBarberPrice)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBarberRating)).setText("N/A");*/
            }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */
}
