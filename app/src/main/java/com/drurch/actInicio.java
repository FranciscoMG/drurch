package com.drurch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drurch.db.DBHelper;
import com.drurch.db.DBSeeder;

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
            }, 3000);
        }
    }
}
