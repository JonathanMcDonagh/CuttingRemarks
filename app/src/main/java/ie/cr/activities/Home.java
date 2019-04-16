package ie.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.cr.R;
import ie.cr.fragments.AddFragment;
import ie.cr.fragments.BarberFragment;
import ie.cr.fragments.EditFragment;
import ie.cr.fragments.MapFragment;
import ie.cr.fragments.SearchFragment;
import ie.cr.main.CuttingRemarksApp;


public class Home extends Base
        implements NavigationView.OnNavigationItemSelectedListener,
        EditFragment.OnFragmentInteractionListener {

    FragmentTransaction ft;

    public static CuttingRemarksApp app = CuttingRemarksApp.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class ));
        }
        FirebaseUser user = mAuth.getCurrentUser();

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
        } else if(id == R.id.nav_map){
            fragment = MapFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Starts Gallery Activity
    public void onStart() {
        super.onStart();
        Button viewBtn = findViewById(R.id.viewGallerybtn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Gallery.class);
                startActivity(intent);
            }

        });
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