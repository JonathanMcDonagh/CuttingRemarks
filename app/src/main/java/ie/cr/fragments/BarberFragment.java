package ie.cr.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import ie.cr.R;
import ie.cr.activities.Base;
import ie.cr.activities.Edit;
import ie.cr.adapters.BarberListAdapter;
import ie.cr.models.Barber;

public class BarberFragment extends ListFragment implements View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static BarberListAdapter listAdapter;
    public ListView listView;

    public BarberFragment() { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("barberId", (String) v.getTag());
        Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent
        /* Add the bundle to the intent here */
        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit); // Launch the Intent
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
        listAdapter = new BarberListAdapter(activity, this, Base.barberList);
        setListAdapter (listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
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
                Base.barberList.remove(barber); // remove from our list
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

    private void deleteBarbers(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                Base.barberList.remove(listAdapter.getItem(i));
            }
        }
        actionMode.finish();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}

}
