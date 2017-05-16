package com.drurch;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drurch.models.Node;

import java.util.ArrayList;

/**
 * Created by fmele on 14/5/2017.
 */

public class baListaLugares extends BaseAdapter {
    Context appContext;
    ArrayList<Node> nodes;


    public baListaLugares(Context context, ArrayList<Node> nodes) {

        this.appContext = context;
        this.nodes = nodes;
    }

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public Object getItem(int position) {
        return nodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null) {
            LayoutInflater lyInflate = (LayoutInflater) appContext.getSystemService(appContext.LAYOUT_INFLATER_SERVICE);
            convertView = lyInflate.inflate(R.layout.lyt_lugares, null);
        }

        ImageView ivInflateImagen = (ImageView) convertView.findViewById(R.id.ivImagen);
        TextView tvInflateNombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView tvInflateDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);

        if (nodes.get(position).getType() == 1)
            ivInflateImagen.setImageResource(R.drawable.bar_icon);
        else
            ivInflateImagen.setImageResource(R.drawable.iglesia_icon);

        tvInflateNombre.setText(nodes.get(position).getTitle());
        tvInflateDescripcion.setText(nodes.get(position).getDescription());

        return convertView;
    }
}
