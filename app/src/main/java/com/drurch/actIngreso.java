package com.drurch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.models.User;

public class actIngreso extends AppCompatActivity implements View.OnClickListener {

    protected EditText etCorreoTelefono, etContrasena;
    protected TextView tvIniciarSesion, tvRegistrarse;
    private DBHelper dbDrurch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ingreso);

        dbDrurch = new DBHelper(this);
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
                if (TextUtils.isEmpty(etCorreoTelefono.getText().toString().trim())) {
                    Toast.makeText(this, R.string.email_number_field_error, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(etContrasena.getText().toString().trim())) {
                    Toast.makeText(this, R.string.password_field_error, Toast.LENGTH_SHORT).show();
                    break;
                }
                if (etContrasena.getText().toString().length() < 8) {
                    Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                    break;
                }
                User usuarioIngreso = dbDrurch.checkUserCredentialsUser(etCorreoTelefono.getText().toString().trim(), etContrasena.getText().toString().trim());
                if (usuarioIngreso != null) {
                    spPreferencias.guardarSesion(this, "user_id", usuarioIngreso.getId());
                    startActivity(new Intent(this, actPrincipal.class));
                    finish();
                } else {
                    Toast.makeText(this, R.string.user_password_error, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.tvRegistrarse: {
                startActivity(new Intent(this, actRegistro.class));
                break;
            }
        }
    }
}
