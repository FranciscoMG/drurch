package com.drurch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by fmele on 14/5/2017.
 */
/*Clase spPreferencias
* Esta clase se encarga de guardar datos de la sesión del usuario asi como optenerlos*/
public class spPreferencias {
    /*Save info*/
    public static void guardarSesion(Context context, String key, int value) {
        SharedPreferences spSesion = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = spSesion.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /*Get info*/
    public static int obtenerSesion(Context context, String key, int defaultValue) {
        SharedPreferences spSesion = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return spSesion.getInt(key, defaultValue);
        } catch (Exception e) {
            return -1;
        }
    }
}
