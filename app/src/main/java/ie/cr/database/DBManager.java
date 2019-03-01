package ie.cr.database;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ie.cr.models.Barber;

public class DBManager {

    private SQLiteDatabase database;
    private DBDesigner dbHelper;

    public DBManager(Context context) {
        dbHelper = new DBDesigner(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void insert(Barber b) {
        ContentValues values = new ContentValues();
        values.put("barbername", b.barberName);
        values.put("shop", b.shop);
        values.put("price", b.price);
        values.put("rating", b.rating);
        values.put("favourite", b.favourite == true ? 1 : 0);

        database.insert("table_barber", null, values);
    }

    public void delete(int id) {
        Log.v("DB", "Barber deleted with id: " + id);
        database.delete("table_barber", "barberid = " + id, null);
    }

    public void update(Barber b) {
        ContentValues values = new ContentValues();
        values.put("barbername", b.barberName);
        values.put("shop", b.shop);
        values.put("price", b.price);
        values.put("rating", b.rating);
        values.put("favourite", b.favourite == true ? 1 : 0);

        database.update("table_barber", values,
                "barberid = " + b.barberId, null);

    }

    public List<Barber> getAll() {
        List<Barber> barbers = new ArrayList<Barber>();
        Cursor cursor = database.rawQuery("SELECT * FROM table_barber", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            barbers.add(toBarber(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return barbers;
    }

    public Barber get(int id) {
        Barber pojo = null;

        Cursor cursor = database.rawQuery("SELECT * FROM table_barber"
                + " WHERE barberid = " + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Barber temp = toBarber(cursor);
            pojo = temp;
            cursor.moveToNext();
        }
        cursor.close();
        return pojo;
    }

    public List<Barber> getFavourites() {
        List<Barber> barbers = new ArrayList<Barber>();
        Cursor cursor = database.rawQuery("SELECT * FROM table_barber"
                + " WHERE favourite = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            barbers.add(toBarber(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return barbers;
    }

    private Barber toBarber(Cursor cursor) {
        Barber pojo = new Barber();
        pojo.barberId = cursor.getInt(0);
        pojo.barberName = cursor.getString(1);
        pojo.shop = cursor.getString(2);
        pojo.price = cursor.getDouble(3);
        pojo.rating = cursor.getDouble(4);
        pojo.favourite = cursor.getInt(5) == 1 ? true : false;

        return pojo;
    }

    public void setupBarbers() {
        Barber b1 = new Barber("Lauren", "The Bearded Lady",5,10,false);
        Barber b2 = new Barber("Johnny", "Portland Barbers",3.5,12,true);
        Barber b3 = new Barber("Jill", "Chapz",2.5,15,true);
        Barber b4 = new Barber("James", "Bladez",2.5,20,true);
        Barber b5 = new Barber("Tom", "Chapz",2.5,15,false);
        Barber b6 = new Barber("Mike", "Bladez",2.5,20,true);

        insert(b1);
        insert(b2);
        insert(b3);
        insert(b4);
        insert(b5);
        insert(b6);
    }
}
