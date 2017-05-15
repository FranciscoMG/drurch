package com.drurch;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;
import com.drurch.models.Comentario;
import com.drurch.models.Comment;
import com.drurch.models.Node;
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
    private ArrayList<Comment> lista_comentarios;
    private ArrayAdapter<Comment> adapter_comentarios;
    private TextView node_title;
    private TextView node_decription;
    private TextView textView_imagen;
    private DBHelper dbHelper;
    private Node node;

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
        dbHelper = new DBHelper(getApplicationContext());
        listView_comentarios = (ListView)findViewById(R.id.listView_coments);
        textView_imagen = (TextView)findViewById(R.id.textView_imagen);
        node_title = (TextView)findViewById(R.id.node_title);
        node_decription = (TextView)findViewById(R.id.node_description);

        // TODO: get the node id from the intent
        int node_id = 2;

        //
//        DBSeeder dbSeeder = new DBSeeder(getApplicationContext());
//        dbSeeder.up();
        //
        //
        node = new Node();
        node = dbHelper.getNode(node_id);
        if (node != null) {
            node_title.setText(node.getTitle());
            node_decription.setText(node.getDescription());
            textView_imagen.setBackgroundResource(getImageInt(node.getScore()));
            //
            lista_comentarios = dbHelper.getCommentsByNode(node_id);
            adapter_comentarios = new ArrayAdapter<>(actMapa.this, android.R.layout.simple_list_item_1, lista_comentarios);
            listView_comentarios.setAdapter(adapter_comentarios);
        }


//        scroll_view.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    // Mapa ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Puntarenas, Costa Rica, and move the camera.
        if (node != null) {
            LatLng position = new LatLng(node.getLatitude(), node.getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title(node.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
        }
    }

    private int getImageInt(int score) {
        /*switch (score) {
            case 0:
                return R.drawable.puntuacion_0;
            case 1:
                return R.drawable.puntuacion_1;
            case 2:
                return R.drawable.puntuacion_2;
            case 3:
                return R.drawable.puntuacion_3;
            case 4:
                return R.drawable.puntuacion_4;
            case 5:
                return R.drawable.puntuacion_5;
        }
        return R.drawable.puntuacion_5;*/
        return 0;
    }

}
