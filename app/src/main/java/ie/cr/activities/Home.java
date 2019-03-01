package ie.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ie.cr.R;
import ie.cr.fragments.AddFragment;
import ie.cr.fragments.BarberFragment;
import ie.cr.fragments.CalendarFragment;
import ie.cr.fragments.EditFragment;
import ie.cr.fragments.HairstylesFragment;
import ie.cr.fragments.SearchFragment;
import ie.cr.models.Barber;

public class Home extends Base
        implements NavigationView.OnNavigationItemSelectedListener,
        EditFragment.OnFragmentInteractionListener {

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ft = getSupportFragmentManager().beginTransaction();

        BarberFragment fragment = BarberFragment.newInstance();
        ft.replace(R.id.homeFrame, fragment);
        ft.commit();

        //app.dbManager.setupBarbers();
        this.setTitle(R.string.recentlyViewedLbl);
    }

    //If back button is pressed when nav drawer is opened it closes the nav drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Handles nav drawer items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // http://stackoverflow.com/questions/32944798/switch-between-fragments-with-onnavigationitemselected-in-new-navigation-drawer

        int id = item.getItemId();
        Fragment fragment;
        ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = BarberFragment.newInstance();
            ((BarberFragment)fragment).favourites = false;
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_add) {
            fragment = AddFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_favourites) {
            fragment = BarberFragment.newInstance();
            ((BarberFragment)fragment).favourites = true;
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_search) {
            fragment = SearchFragment.newInstance();
            ((BarberFragment) fragment).favourites = false;
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if(id == R.id.nav_hairstyles){
            fragment = HairstylesFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if(id == R.id.nav_calender) {
            fragment = CalendarFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //When a barber is clicked allows user to edit the barber rating
    @Override
    public void toggle(View v) {
        EditFragment editFrag = (EditFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (editFrag != null) {
            editFrag.toggle(v);
        }
    }

    //To save the edited barber
    @Override
    public void saveBarber(View v) {
        EditFragment editFrag = (EditFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (editFrag != null) {
            editFrag.saveBarber(v);
        }
    }
}