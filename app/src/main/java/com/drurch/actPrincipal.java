package com.drurch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drurch.db.DBHelper;
import com.drurch.models.User;

/*Clase actPrincipal
* Clase encargada de controlar el layout act_principal.xml
* Seleccionar opciones principales*/
public class actPrincipal extends AppCompatActivity implements View.OnClickListener {

    protected TextView btnPerfil, tvNombre, tvBar, tvIglesia;
    protected ImageView ivPerfil, btnBar, btnIglesia;
    private User usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);

        btnPerfil = (TextView) findViewById(R.id.btnPerfil);
        ivPerfil = (ImageView) findViewById(R.id.ivPerfil);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        btnBar = (ImageView) findViewById(R.id.btnBar);
        tvBar = (TextView) findViewById(R.id.tvBar);
        btnIglesia = (ImageView) findViewById(R.id.btnIglesia);
        tvIglesia = (TextView) findViewById(R.id.tvIglesia);

        btnPerfil.setOnClickListener(this);
        ivPerfil.setOnClickListener(this);
        btnBar.setOnClickListener(this);
        tvBar.setOnClickListener(this);
        btnIglesia.setOnClickListener(this);
        tvIglesia.setOnClickListener(this);

        usuarioActual = new DBHelper(this).getUser(spPreferencias.obtenerSesion(this, "user_id", -1));
        if (usuarioActual != null) {
            tvNombre.setText(usuarioActual.getName());
        }

        int estadoPermiso = 1;
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, estadoPermiso);
        } else {
            estadoPermiso = PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    /*Selección de opciones para el usuario*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPerfil: case R.id.ivPerfil: {
                startActivity(new Intent(this, actPerfil.class));
                break;
            }
            case R.id.btnBar: case R.id.tvBar: {
                Intent iLista = new Intent(this, actLista.class);
                iLista.putExtra("tipoLista", 1);
                startActivity(iLista);
                break;
            }
            case R.id.btnIglesia: case R.id.tvIglesia: {
                Intent iLista = new Intent(this, actLista.class);
                iLista.putExtra("tipoLista", 2);
                startActivity(iLista);
                break;
            }
        }
    }
}
