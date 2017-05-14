package com.drurch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class actLogin extends AppCompatActivity implements View.OnClickListener {

    protected EditText etCorreoTelefono, etContrasena;
    protected TextView tvIniciarSesion, tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        etCorreoTelefono = (EditText) findViewById(R.id.etCorreoTelefono);
        etContrasena = (EditText) findViewById(R.id.etContrasena);
        tvIniciarSesion = (TextView) findViewById(R.id.tvIniciarSesion);
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);

        tvIniciarSesion.setOnClickListener(this);
        tvRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvIniciarSesion: {
                break;
            }
            case R.id.tvRegistrarse: {
                startActivity(new Intent(this, actRegistro.class));
                break;
            }
        }
    }
}
