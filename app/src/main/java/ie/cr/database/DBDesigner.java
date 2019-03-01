package ie.cr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDesigner extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "CuttingRemarks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_BARBER = "create table table_barber"
            + " ( barberid integer primary key autoincrement, "
            + "barbername text not null,"
            + "shop text not null,"
            + "price double not null,"
            + "rating double not null,"
            + "favourite integer not null);";

    public DBDesigner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_BARBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBDesigner.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS table_barber");
        onCreate(db);
    }
}