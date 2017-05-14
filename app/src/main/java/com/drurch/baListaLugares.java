package com.drurch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by fmele on 14/5/2017.
 */

public class baListaLugares extends BaseAdapter {
    Context appContext;


    public baListaLugares(Context context) {
        this.appContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        return convertView;
    }
}
