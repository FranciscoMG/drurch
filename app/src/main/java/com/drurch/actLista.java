package com.drurch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;
import com.drurch.models.Node;
import com.drurch.models.User;

import java.io.IOException;
import java.util.ArrayList;

public class actLista extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        LocationListener {

    protected DrawerLayout dlOpciones;
    protected ActionBarDrawerToggle adtLateral;
    protected ListView lvListaLugares;
    protected ImageView ivImagen;
    protected TextView tvNombre, tvCorreoTelefono;
    private View vInfoUsuario;
    private baListaLugares baLista;
    private User usuarioActual;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        nodeType = getIntent().getIntExtra("tipoLista", 1);
        if (nodeType == 1)
            getSupportActionBar().setTitle(R.string.bars_text);
        else
            getSupportActionBar().setTitle(R.string.churches_text);

        lvListaLugares = (ListView) findViewById(R.id.lvListaLugares);
        NavigationView nvLateral = (NavigationView) findViewById(R.id.nvLateral);
        vInfoUsuario = nvLateral.getHeaderView(0);
        dlOpciones = (DrawerLayout) findViewById(R.id.drawer_layout);
        adtLateral = new ActionBarDrawerToggle(this, dlOpciones, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ivImagen = (ImageView) vInfoUsuario.findViewById(R.id.ivImagen);
        tvNombre = (TextView) vInfoUsuario.findViewById(R.id.tvNombre);
        tvCorreoTelefono = (TextView) vInfoUsuario.findViewById(R.id.tvCorreoTelefono);

        // Nodes list
        nodeList = new ArrayList<>();
        // Database
        dbHelper = new DBHelper(getApplicationContext());
        // TODO: set node type and distance
//        DBSeeder seeder = new DBSeeder(getApplicationContext());
//        seeder.up();
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


        usuarioActual = new DBHelper(this).getUser(spPreferencias.obtenerSesion(this, "user_id", -1));
        if (usuarioActual != null) {
            try {
                ivImagen.setImageBitmap(hacerCirculoImagen(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(usuarioActual.getImg()))));
            } catch (Exception e) {
                ivImagen.setImageResource(R.drawable.persona_perfil);
            }
            tvNombre.setText(usuarioActual.getName());
            tvCorreoTelefono.setText(usuarioActual.getEmail());
        }
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
                startActivity(new Intent(this, actPerfil.class));
                finish();
                break;
            }
            case  R.id.nav_salir: {
                spPreferencias.guardarSesion(this, "user_id", -1);
                startActivity(new Intent(this, actIngreso.class));
                finish();
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
        dialog.setMessage(R.string.gps_dialog_error)
                .setPositiveButton(R.string.enable_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
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

    public Bitmap hacerCirculoImagen(Bitmap bitmap) {
        int anchoImagen = 150;
        int altoImagen = 150;
        Bitmap rawBitmap = Bitmap.createBitmap(anchoImagen, altoImagen, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rawBitmap);
        Path path = new Path();
        path.addCircle(((float) anchoImagen - 1) / 2, ((float) altoImagen - 1) / 2, (Math.min(((float) anchoImagen), ((float) altoImagen)) / 2), Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, anchoImagen, altoImagen), null);
        return rawBitmap;
    }
}
