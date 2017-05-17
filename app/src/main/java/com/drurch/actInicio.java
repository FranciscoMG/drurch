package com.drurch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;

/*Clase actInicio
  Clase que controla al layout act_inicio.xml
* Esta clase se encarga de sembrar información en la base de datos de los bares e iglesias,
* ademas verifica si la sesión esta activa o inactiva para dirigirlo al layout principal o al layout
* de ingreso.*/
public class actInicio extends AppCompatActivity {

    private DBHelper dbHelper;
    private DBSeeder dbSeeder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inicio);

        dbHelper = new DBHelper(getApplicationContext());
        if (dbHelper.getAllNodes().size() == 0){
            dbSeeder = new DBSeeder(getApplicationContext());
            dbSeeder.up();
        }

        {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (spPreferencias.obtenerSesion(actInicio.this, "user_id", -1) != -1) {
                        startActivity(new Intent(actInicio.this, actPrincipal.class));
                        finish();
                    } else {
                        startActivity(new Intent(actInicio.this, actIngreso.class));
                        finish();
                    }
                }
                /*Delay para el inicio*/
            }, 3000);
        }
    }
}
