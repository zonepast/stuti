package com.example.aff02.secondtask.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aff02.secondtask.database.DatabaseHelper;
import com.example.aff02.secondtask.fragments.HomeFragment;
import com.example.aff02.secondtask.fragments.PasswordFragment;
import com.example.aff02.secondtask.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        String email,pass,user,identity;
        DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseHelper = new DatabaseHelper(this);
        TextView txtidentity = (TextView)navigationView.getHeaderView(0).findViewById(R.id.txtidentity);
        //View header=navigationView.getHeaderView(0);
        Intent intent = getIntent();
        String identitytea = intent.getStringExtra("teacher");
        String identitystu = intent.getStringExtra("student");
        if (identitystu == identitytea)
        {
            txtidentity.setText(identitystu);
        }
        if (identitytea == identitytea)
        {
            txtidentity.setText(identitytea);
        }

        SharedPreferences preferences = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        String profilename=preferences.getString("profilename",null);
        TextView txtprofilename = (TextView)navigationView.getHeaderView(0).findViewById(R.id.profilename);
//        Log.e("profilename",user);
        txtprofilename.setText(databaseHelper.getUsername());

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

        if (id == R.id.nav_home) {
          HomeFragment homeFragment = HomeFragment.newInstance("","");
            addFragment(R.id.content_frame,homeFragment);
        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_rateapp) {

        } else if (id == R.id.nav_change_pass) {
            PasswordFragment passfrag = PasswordFragment.newInstance(pass,email);
            SharedPreferences preferences = getSharedPreferences("ActivityPREF",Context.MODE_PRIVATE);
            email = preferences.getString("email","");
            Log.e("EMAIL",preferences.getString("email",""));
            pass = preferences.getString("pass","");
            Log.e("PASSS",preferences.getString("pass",""));
            addFragment(R.id.content_frame,passfrag);
        }

        else if (id == R.id.nav_sign_out) {
            SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            edt.clear();
            Intent intent = new Intent(this,SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            edt.apply();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}

    public void addFragment(int id, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(id, fragment);
        transaction.addToBackStack("");

        transaction.commit();
    }
    }
