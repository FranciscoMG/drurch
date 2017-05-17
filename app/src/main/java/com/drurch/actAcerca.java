package com.drurch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/* Clase actAcerca
* Clase que controla al layout act_acerca.xml
* */
public class actAcerca extends AppCompatActivity {

    private ImageView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acerca);

        btnVolver = (ImageView) findViewById(R.id.btnVolver);

        /* Se le asigna una función de volver una ventana al botón btnVolver*/
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
