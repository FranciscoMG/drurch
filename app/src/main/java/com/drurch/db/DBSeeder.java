package com.drurch.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vinicio on 14/5/2017.
 */

public class DBSeeder {

    DBHelper dbHelper;

    public DBSeeder(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void down(){
        dbHelper.truncate();
    }
    public void seedUsers() {
        for (int i = 0; i < 10; i++) {
            dbHelper.insertUser("email" + i, "name" + i, "password" + i, "img" + i);
        }
    }
    public void seedNodes(){
        // Bares de Esparza
        dbHelper.insertNode(1, "Bar y Hotel las brisas", "Frente al barrio Las Brisas", 9.994831348269575, -84.67206430461374, 3, 0);
        dbHelper.insertNode(1, "Bar Deportivo", "Frente a la cancha de futbol", 9.992210967449507, -84.66627073314157, 4, 0);
        dbHelper.insertNode(1, "Bar Restaurante Tabaris", "Frente a la interamericana", 9.994886291761386, -84.66641664505005, 4, 0);
        dbHelper.insertNode(1, "Bar Aquí Me Quedo", "A 200 metros del Pali", 9.993824406306823, -84.66193199157715, 5, 0);
        dbHelper.insertNode(1, "Turis Bar Rest", "En marañonal", 9.996962505540017, -84.65887427330017, 3, 0);
        dbHelper.insertNode(1, "Bar la Cueva del Sapo", "En Marañonal", 10.000047745284236, -84.65915858745575, 4, 0);
        // Iglesias de Esparza
        dbHelper.insertNode(2, "Iglesia Central de Esparza", "Frente al parque", 9.98994558920937, -84.66601109517796, 4, 0);
        dbHelper.insertNode(2, "Iglesia Marañonal", "Iglesia católica", 10.002483162133759, -84.65723276138306, 3, 0);
        dbHelper.insertNode(2, "Iglesia Vida Nueva", "", 9.996281001715888, -84.66584801673889, 5, 0);
        dbHelper.insertNode(2, "Templo Católico la Riviera", "En el centro de la urbanización", 9.998373055427239, -84.66416358947754, 2, 0);
        dbHelper.insertNode(2, "Catedral Manatial de Vida", "", 9.999413794303045, -84.66214120388031, 4, 0);
        dbHelper.insertNode(2, "Templo Evengélico", "", 9.993158744999741, -84.6669852733612, 3, 0);
        dbHelper.insertNode(2, "Templo Católico Mojón", "En el Mojón de Esparza", 9.98667640056043, -84.67638373374939, 4, 0);
        //
        for (int i = 0; i < 10; i++) {
            dbHelper.insertNode(1, "title"+i, "description"+i, 1.12312, 0.1233, 2, i);
        }
        for (int i = 0; i < 10; i++) {
            dbHelper.insertNode(2, "title"+i, "description"+i, 1.12312, 0.1233, 5, i);
        }
    }
    public void seedComments(){
        for (int i = 0; i < 50; i++) {
            dbHelper.insertComment("description"+i, 1233+i, 1);
        }
        for (int i = 0; i < 50; i++) {
            dbHelper.insertComment("description"+i, 1233+i, 2);
        }
    }
    public void up(){
        down();
        seedUsers();
        seedNodes();
        seedComments();
    }
}
