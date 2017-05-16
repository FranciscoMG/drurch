package com.drurch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drurch.db.DBHelper;
import com.drurch.models.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class actPerfil extends AppCompatActivity implements View.OnClickListener {

    protected ImageView ivImagenPerfil;
    protected FloatingActionButton fabCambiarImagen;
    protected EditText etNombreRegistro, etContrasenaActual, etContrasenaModificar, etRepetirContrasenaModificar;
    protected TextView btnModificar;
    private User usuarioActual;
    private String uRutaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_perfil);

        ivImagenPerfil = (ImageView) findViewById(R.id.ivImagenPerfil);
        fabCambiarImagen = (FloatingActionButton) findViewById(R.id.fabCambiarImagen);
        etNombreRegistro = (EditText) findViewById(R.id.etNombreRegistro);
        etContrasenaActual = (EditText) findViewById(R.id.etContrasenaActual);
        etContrasenaModificar = (EditText) findViewById(R.id.etContrasenaModificar);
        etRepetirContrasenaModificar = (EditText) findViewById(R.id.etRepetirContrasenaModificar);
        btnModificar = (TextView) findViewById(R.id.btnModificar);

        usuarioActual = new DBHelper(this).getUser(spPreferencias.obtenerSesion(this, "user_id", -1));
        if (usuarioActual != null) {
            try {
                ivImagenPerfil.setImageBitmap(hacerCirculoImagen(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(usuarioActual.getImg()))));
            } catch (IOException e) {
                ivImagenPerfil.setImageResource(R.drawable.persona_perfil);
            }
            etNombreRegistro.setText(usuarioActual.getName());
        }

        ivImagenPerfil.setOnClickListener(this);
        fabCambiarImagen.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivImagenPerfil: case R.id.fabCambiarImagen: {
                AlertDialog.Builder adEscogerImagen = new AlertDialog.Builder(this);
                adEscogerImagen.setMessage(R.string.select_photo_source_text)
                        .setPositiveButton(R.string.gallery_text, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent iGaleria = new Intent();
                                iGaleria.setType("image/*");
                                iGaleria.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(iGaleria, "Abrir aplicación de galería"), 1);
                            }
                        })
                        .setNegativeButton(R.string.camera_text, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent iCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(iCamara, 2);
                            }
                        });
                adEscogerImagen.show();
                break;
            }
            case R.id.btnModificar: {
                if (TextUtils.isEmpty(etNombreRegistro.getText().toString())) {
                    Toast.makeText(this, R.string.user_field_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etContrasenaActual.getText().toString()) && !TextUtils.isEmpty(etContrasenaModificar.getText().toString())) {
                    Toast.makeText(this, R.string.current_password_field_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(etContrasenaActual.getText().toString()) && etContrasenaActual.getText().toString().length() < 8) {
                    Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(etContrasenaModificar.getText().toString()) && etContrasenaModificar.getText().toString().length() < 8) {
                    Toast.makeText(this, R.string.password_length_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etRepetirContrasenaModificar.getText().toString()) && !TextUtils.isEmpty(etContrasenaModificar.getText().toString())) {
                    Toast.makeText(this, R.string.repeat_password_field_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!etContrasenaModificar.getText().toString().equals(etRepetirContrasenaModificar.getText().toString())) {
                    Toast.makeText(this, R.string.password_match_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (new DBHelper(this).updateUser(usuarioActual.getId(), usuarioActual.getEmail(), etNombreRegistro.getText().toString(), etContrasenaModificar.getText().toString(), uRutaFoto)) {
                    startActivity(new Intent(this, actPrincipal.class));
                    finish();
                    return;
                }

                Toast.makeText(this, R.string.modify_user_error, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            switch (reqCode) {
                case 1:
                    uRutaFoto = String.valueOf(data.getData());
                    try {
                        ivImagenPerfil.setImageBitmap(hacerCirculoImagen(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData())));
                    } catch (IOException e) {
                    }
                    break;
                case 2:
                    if (data != null) {
                        Bitmap rawBitmap = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream baosBitmap = new ByteArrayOutputStream();
                        rawBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baosBitmap);

                        File fileManager = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                        FileOutputStream fosFlujo;
                        try {
                            fileManager.createNewFile();
                            fosFlujo = new FileOutputStream(fileManager);
                            fosFlujo.write(baosBitmap.toByteArray());
                            fosFlujo.close();
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {
                        }
                        uRutaFoto = String.valueOf(data.getData());
                        ivImagenPerfil.setImageBitmap(hacerCirculoImagen(rawBitmap));
                    } else {
                    }
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.upload_photo_error, Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap hacerCirculoImagen(Bitmap bitmap) {
        int anchoImagen = 150;
        int altoImagen = 150;
        Bitmap rawBitmap = Bitmap.createBitmap(anchoImagen, altoImagen, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rawBitmap);
        Path path = new Path();
        path.addCircle(((float) anchoImagen - 1) / 2, ((float) altoImagen - 1) / 2, (Math.min(((float) anchoImagen), ((float) altoImagen)) / 2), Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, anchoImagen, altoImagen), null);
        return rawBitmap;
    }
}
