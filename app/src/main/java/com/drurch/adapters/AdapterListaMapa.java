package com.drurch.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drurch.R;
import com.drurch.models.Comment;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by Vinicio on 15/5/2017.
 */

public class AdapterListaMapa extends BaseAdapter {
    Context context;
    ArrayList<Comment> comments;

    public AdapterListaMapa(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.content_lista_mapa,null);
        }
        ImageView imageView_lista_imagen = (ImageView)convertView.findViewById(R.id.imageView_lista_imagen);
        TextView textView_lista_comment_descripcion = (TextView) convertView.findViewById(R.id.textView_lista_comment_descripcion);

        // TODO: add user image
        String img = comments.get(position).getImg();
        if (img != null){
            if(img != ""){
                imageView_lista_imagen.setImageURI(Uri.parse(img));
                try {
                    imageView_lista_imagen.setImageBitmap(hacerCirculoImagen(MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(img))));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                imageView_lista_imagen.setImageResource(R.drawable.persona_perfil_negro);
            }
        } else {
            imageView_lista_imagen.setImageResource(R.drawable.persona_perfil_negro);
        }
        //
        textView_lista_comment_descripcion.setText(comments.get(position).getId()+comments.get(position).getUser_name()+" : "+comments.get(position).getDescription());

        return convertView;
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
