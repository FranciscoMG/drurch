package com.drurch;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.adapters.AdapterListaMapa;
import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;
import com.drurch.models.Comentario;
import com.drurch.models.Comment;
import com.drurch.models.Node;
import com.drurch.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

/*Clase act Mapa
  Clase que controla al layout act_mapa.xml
* Clase encargada de manejar lo referente a la vista del mapa*/
public class actMapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior scroll_view;
    private ListView listView_comentarios;
    private ArrayList<Comment> lista_comentarios;
    private ArrayAdapter<Comment> adapter_comentarios;
    private TextView node_title;
    private TextView node_decription;
    private TextView textView_imagen;
    private EditText editText_nuevo_comentario;
    private Button button_agregar;
    private DBHelper dbHelper;
    private Node node;
    private AdapterListaMapa adapterListaMapa;
    private User usuarioActual;
    private int node_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mapa);

        // Map fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        //avoid automatically appear android keyboard when activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Inicialización de variables
        scroll_view = BottomSheetBehavior.from(findViewById(R.id.linear_sheet));
        lista_comentarios = new ArrayList<>();
        dbHelper = new DBHelper(getApplicationContext());
        listView_comentarios = (ListView)findViewById(R.id.listView_coments);
        textView_imagen = (TextView)findViewById(R.id.textView_imagen);
        node_title = (TextView)findViewById(R.id.node_title);
        node_decription = (TextView)findViewById(R.id.node_description);
        editText_nuevo_comentario = (EditText)findViewById(R.id.editText_agregar_nuevo_comentario);
        button_agregar = (Button)findViewById(R.id.button_agregar);
        usuarioActual = new DBHelper(this).getUser(spPreferencias.obtenerSesion(this, "user_id", -1));

        node_id = 0;
        if(getIntent().hasExtra("node_id")) {
            node_id = getIntent().getIntExtra("node_id", 0);
        }

        //Verificación del nodo(posicion en mapa)
        node = new Node();
        node = dbHelper.getNode(node_id);
        if (node != null) {
            node_title.setText(node.getTitle());
            node_decription.setText(node.getDescription());
            textView_imagen.setBackgroundResource(getImageInt(node.getScore()));
            //
            lista_comentarios = dbHelper.getCommentsByNode(node_id);
            adapterListaMapa = new AdapterListaMapa(getApplicationContext(), lista_comentarios);
            listView_comentarios.setAdapter(adapterListaMapa);
        }

        /*Agregar Comentario*/
        button_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText_nuevo_comentario.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Agregue texto al comentario", Toast.LENGTH_SHORT).show();
                } else {
                    Date currentDate = new Date();
                    int unix = (int)currentDate.getTime() / 1000;
                    dbHelper.insertComment(editText_nuevo_comentario.getText().toString(), unix , usuarioActual.getId(), node_id);
                    lista_comentarios = dbHelper.getCommentsByNode(node_id);
                    adapterListaMapa = new AdapterListaMapa(getApplicationContext(), lista_comentarios);
                    listView_comentarios.setAdapter(adapterListaMapa);
                    editText_nuevo_comentario.setText("");
                }
            }
        });
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

    /*Selector de puntuación*/
    private int getImageInt(int score) {
        switch (score) {
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
        return R.drawable.puntuacion_5;
    }

}
