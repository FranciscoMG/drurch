package com.drurch;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.models.Comentario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class actMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior scroll_view;
    private ListView listView_comentarios;
    private ArrayList<Comentario> lista_comentarios;
    private ArrayAdapter<Comentario> adapter_comentarios;
    private TextView textView_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mapa);

        // Map fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        // Inicialización de avriables
        scroll_view = BottomSheetBehavior.from(findViewById(R.id.linear_sheet));
        lista_comentarios = new ArrayList<>();
        listView_comentarios = (ListView)findViewById(R.id.listView_coments);
        textView_imagen = (TextView)findViewById(R.id.textView_imagen);
        textView_imagen.setBackgroundResource(R.drawable.puntuacion_5);

        // Inicialización de métodos
        generador_comentarios();
        adapter_comentarios = new ArrayAdapter<Comentario>(actMapa.this, android.R.layout.simple_list_item_1, lista_comentarios);
        listView_comentarios.setAdapter(adapter_comentarios);

//        scroll_view.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    // Mapa ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Puntarenas, Costa Rica, and move the camera.
        LatLng position = new LatLng(9.9778439, -84.82942109999999);
        mMap.addMarker(new MarkerOptions().position(position).title("Puntarenas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
    }

    // Generador de comentarios ////////////////////////////////////////////////////////////////////
    public void generador_comentarios(){
        Comentario comentario;
        for (int i = 0; i < 2 ; i++) {
            comentario = new Comentario(null, "Nombre" + i, "Comentario de ejemplo rápido " + i);
            lista_comentarios.add(comentario);
        }
    }
}
