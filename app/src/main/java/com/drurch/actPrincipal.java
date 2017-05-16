package com.drurch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drurch.db.DBHelper;
import com.drurch.models.User;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPerfil: case R.id.ivPerfil: {
                startActivity(new Intent(this, actPerfil.class));
                break;
            }
            case R.id.btnBar: case R.id.tvBar: {

                break;
            }
            case R.id.btnIglesia: case R.id.tvIglesia: {

                break;
            }
        }
    }
}
