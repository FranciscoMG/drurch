package com.drurch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class actInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inicio);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(actInicio.this, actLogin.class));
                finish();
            }
        }, 3000);
    }
}
