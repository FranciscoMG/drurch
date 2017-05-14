package com.drurch.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicio on 13/5/2017.
 */

public class Comentario implements Parcelable {

    private Uri img;
    private String usuario;
    private String comentario;

    // Implementación del parcel
    protected Comentario(Parcel in) {
        img = in.readParcelable(Uri.class.getClassLoader());
        usuario = in.readString();
        comentario = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(img, flags);
        dest.writeString(usuario);
        dest.writeString(comentario);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    // Constructor, setters y getters


    public Comentario() {
    }

    public Comentario(Uri img, String usuario, String comentario) {
        this.img = img;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public static Creator<Comentario> getCREATOR() {
        return CREATOR;
    }
}
