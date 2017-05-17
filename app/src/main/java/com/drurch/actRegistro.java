package com.drurch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.models.User;

/*Clase actResgistro
* Clase encargada de controlar layout act_registro.xml
* Registra a los usuario en la aplicación*/
public class actRegistro extends AppCompatActivity implements View.OnClickListener {

    protected EditText etNombreRegistro, etCorreoTelefonoRegistro, etContrasenaRegistro, etRepetirContrasenaRegistro;
    protected TextView tvRegistrarse;
    private String uRutaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_registro);

        etNombreRegistro = (EditText) findViewById(R.id.etNombreRegistro);
        etCorreoTelefonoRegistro = (EditText) findViewById(R.id.etCorreoTelefonoRegistro);
        etContrasenaRegistro = (EditText) findViewById(R.id.etContrasenaRegistro);
        etRepetirContrasenaRegistro = (EditText) findViewById(R.id.etRepetirContrasenaRegistro);
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);

        tvRegistrarse.setOnClickListener(this);
    }

    /*Verifica los campos del registro del usuario*/
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegistrarse) {
            if (TextUtils.isEmpty(etNombreRegistro.getText().toString())) {
                Toast.makeText(this, R.string.user_field_error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(etCorreoTelefonoRegistro.getText().toString().trim())) {
                Toast.makeText(this, R.string.email_number_field_error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(etContrasenaRegistro.getText().toString())) {
                Toast.makeText(this, R.string.password_field_error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (etContrasenaRegistro.getText().toString().length() < 8) {
                Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(etRepetirContrasenaRegistro.getText().toString())) {
                Toast.makeText(this, R.string.repeat_password_field_error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!etContrasenaRegistro.getText().toString().equals(etRepetirContrasenaRegistro.getText().toString())) {
                Toast.makeText(this, R.string.password_match_error, Toast.LENGTH_SHORT).show();
                return;
            }

            /*Guarda la información proporcionada por el usuario*/
            if (new DBHelper(this).insertUser(etCorreoTelefonoRegistro.getText().toString().trim(), etNombreRegistro.getText().toString(), etContrasenaRegistro.getText().toString(), uRutaFoto)) {
                User usuario = new DBHelper(this).checkUserCredentialsUser(etCorreoTelefonoRegistro.getText().toString().trim(), etContrasenaRegistro.getText().toString());
                if (usuario != null) {
                    spPreferencias.guardarSesion(this, "user_id", usuario.getId());
                    startActivity(new Intent(this, actPrincipal.class));
                    finish();
                }
                return;
            }

            Toast.makeText(this, R.string.create_user_error, Toast.LENGTH_SHORT).show();
        }
    }
}
