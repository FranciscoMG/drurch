package com.drurch.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drurch.R;
import com.drurch.models.Comment;

import java.util.ArrayList;

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
        imageView_lista_imagen.setImageResource(R.drawable.persona_perfil_negro);
        //
        textView_lista_comment_descripcion.setText(comments.get(position).getUser_name()+" : "+comments.get(position).getDescription());

        return convertView;
    }
}
