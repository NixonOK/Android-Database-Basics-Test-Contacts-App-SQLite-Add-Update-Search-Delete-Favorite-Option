package com.w3e.nixonok.assignmentondbcontactsapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AllContactsFragment.OnFragmentInteractionListener, FavouriteContactsFragment.OnFragmentInteractionListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, AllContactsFragment.newInstance());
        transaction.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem allContactViewTabOption = new AHBottomNavigationItem(R.string.all_contacts, R.drawable.ic_contacts_white_24px, R.color.colorPrimary);
        AHBottomNavigationItem favContactsViewOption = new AHBottomNavigationItem(R.string.fav_contacts, R.drawable.ic_star_white_24px, R.color.colorAccent);

        bottomNavigation.addItem(allContactViewTabOption);
        bottomNavigation.addItem(favContactsViewOption);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        bottomNavigation.setBehaviorTranslationEnabled(true);

        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        bottomNavigation.setForceTint(true);


        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setColored(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            Fragment currentFragment;
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        currentFragment = AllContactsFragment.newInstance();

                        break;
                    case 1:
                        currentFragment = FavouriteContactsFragment.newInstance();
                        break;
                }

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, currentFragment);
                transaction.commit();
                return true;
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView search = (SearchView) menu.findItem(R.id.search1).getActionView();
        search.setIconified(false);
        search.setQueryHint("Search");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                try{
                    if(s != ""){

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                        contacts = db.searchMatchingContacts(s);

                        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), contacts);

                        recyclerView = findViewById(R.id.contactsList);

                        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(recylerViewLayoutManager);

                        recyclerView.setAdapter(recyclerViewAdapter);

                        Toast.makeText(getApplicationContext(),"Search!", Toast.LENGTH_LONG).show();
                    }else{
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                        contacts = db.getAllContacts();

                        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), contacts);

                        recyclerView = findViewById(R.id.contactsList);

                        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(recylerViewLayoutManager);

                        recyclerView.setAdapter(recyclerViewAdapter);
                    }


                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error!"+e.toString(), Toast.LENGTH_LONG).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                try{
                    if(s != ""){

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                        contacts = db.searchMatchingContacts(s);

                        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), contacts);

                        recyclerView = findViewById(R.id.contactsList);

                        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(recylerViewLayoutManager);

                        recyclerView.setAdapter(recyclerViewAdapter);

                        Toast.makeText(getApplicationContext(),"Search!", Toast.LENGTH_LONG).show();
                    }else{
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                        contacts = db.getAllContacts();

                        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), contacts);

                        recyclerView = findViewById(R.id.contactsList);

                        recylerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(recylerViewLayoutManager);

                        recyclerView.setAdapter(recyclerViewAdapter);
                    }

                    Toast.makeText(getApplicationContext(),"Search!", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error!"+e.toString(), Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),"Search!", Toast.LENGTH_LONG).show();

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
