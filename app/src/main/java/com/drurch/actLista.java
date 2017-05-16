package com.drurch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;
import com.drurch.models.Node;

import java.util.ArrayList;

public class actLista extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        LocationListener {

    protected DrawerLayout dlOpciones;
    protected ActionBarDrawerToggle adtLateral;
    protected ListView lvListaLugares;
    private baListaLugares baLista;

    // Location
    private double longitudeBest, latitudeBest;
    private LocationManager locationManager;
    private String provider;
    //
    private ArrayList<Node> nodeList;
    // Database
    private DBHelper dbHelper;
    private int nodeType, distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Iglesia o bar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lvListaLugares = (ListView) findViewById(R.id.lvListaLugares);
        NavigationView nvLateral = (NavigationView) findViewById(R.id.nvLateral);
        dlOpciones = (DrawerLayout) findViewById(R.id.drawer_layout);
        adtLateral = new ActionBarDrawerToggle(this, dlOpciones, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Nodes list
        nodeList = new ArrayList<>();
        // Database
        dbHelper = new DBHelper(getApplicationContext());
        // TODO: set node type and distance
//        DBSeeder seeder = new DBSeeder(getApplicationContext());
//        seeder.up();
        nodeType = 1;
        distance = 1500;

        // Location
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(checkLocation()){
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
        }
        //


        adtLateral.setDrawerIndicatorEnabled(true);
        dlOpciones.addDrawerListener(adtLateral);
        adtLateral.syncState();

        lvListaLugares.setOnItemClickListener(this);
        nvLateral.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (dlOpciones.isDrawerOpen(GravityCompat.START)) {
            dlOpciones.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.nav_perfil: {
                break;
            }
            case  R.id.nav_salir: {
                break;
            }
            case  R.id.nav_acerca: {
                startActivity(new Intent(this, actAcerca.class));
                break;
            }
        }

        dlOpciones.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(actLista.this, actMapa.class);
        i.putExtra("node_id", nodeList.get(position).getId());
        startActivity(i);
    }

    // Locations
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
    @Override
    public void onLocationChanged(Location location) {
        latitudeBest = location.getLatitude();
        longitudeBest = location.getLongitude();
        //
        nodeList = dbHelper.getNearestNodes(nodeType, latitudeBest, longitudeBest, distance);
        baLista = new baListaLugares(this, nodeList);
        lvListaLugares.setAdapter(baLista);
        //
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    // \ Location
}
