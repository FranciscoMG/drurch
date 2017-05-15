package com.drurch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;

public class actLista extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    protected  DrawerLayout dlOpciones;
    protected ActionBarDrawerToggle adtLateral;
    protected ListView lvListaLugares;
    private baListaLugares baLista;

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

        baLista = new baListaLugares(this);
        lvListaLugares.setAdapter(baLista);
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
    }
}
