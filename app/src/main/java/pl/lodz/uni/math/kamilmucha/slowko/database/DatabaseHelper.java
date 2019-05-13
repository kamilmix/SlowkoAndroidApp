package pl.lodz.uni.math.kamilmucha.slowko.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "Database";
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";


    private static final String CREATE_TABLE_ZESTAWY = "CREATE TABLE ZESTAWY (" + KEY_ID + " " + ID_OPTIONS + ", " + "nazwa TEXT NOT NULL)";

    private static final String CREATE_TABLE_SLOWKA = "CREATE TABLE SLOWKA (" +
            KEY_ID + " " + ID_OPTIONS + ", " +
            "slowko TEXT NOT NULL, " +
            "tlumaczenie TEXT NOT NULL, " +
            "czy_umie BOOLEAN, " +
            "id_zestawu INTEGER, " +
            "FOREIGN KEY(id_zestawu) REFERENCES ZESTAWY(_id) ON DELETE CASCADE" +
            " )";

    private static final String INSERT_DATA_TO_SLOWKA_1 = "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES('red', 'czerwony', 0, 1)";
    private static final String INSERT_DATA_TO_SLOWKA_2 = "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES('blue', 'niebieski', 0, 1)";
    private static final String INSERT_DATA_TO_SLOWKA_3 = "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES('orange', 'pomarańczowy', 0, 1)";
    private static final String INSERT_DATA_TO_SLOWKA_4 = "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES('white', 'bialy', 0, 1)";
    private static final String INSERT_DATA_TO_SLOWKA_5 = "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES('dog', 'pies', 0, 2)";

    private static final String[] nazwyZestawow = {"kolory", "zwierzęta", "przedmioty"};

    private static final String DROP_TABLE_ZESTAWY = "DROP TABLE IF EXISTS ZESTAWY";
    private static final String DROP_TABLE_SLOWKA = "DROP TABLE IF EXISTS SLOWKA";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ZESTAWY);
        db.execSQL(CREATE_TABLE_SLOWKA);

        populateZestawy(db);

        db.execSQL(INSERT_DATA_TO_SLOWKA_1);
        db.execSQL(INSERT_DATA_TO_SLOWKA_2);
        db.execSQL(INSERT_DATA_TO_SLOWKA_3);
        db.execSQL(INSERT_DATA_TO_SLOWKA_4);
        db.execSQL(INSERT_DATA_TO_SLOWKA_5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SLOWKA);
        db.execSQL(DROP_TABLE_ZESTAWY);

        db.execSQL(CREATE_TABLE_ZESTAWY);
        db.execSQL(CREATE_TABLE_SLOWKA);

        populateZestawy(db);

        db.execSQL(INSERT_DATA_TO_SLOWKA_1);
        db.execSQL(INSERT_DATA_TO_SLOWKA_2);
        db.execSQL(INSERT_DATA_TO_SLOWKA_3);
        db.execSQL(INSERT_DATA_TO_SLOWKA_4);
        db.execSQL(INSERT_DATA_TO_SLOWKA_5);
    }

    private void populateZestawy(SQLiteDatabase db) {
        for (String zestaw : nazwyZestawow) {
            db.execSQL("INSERT INTO ZESTAWY (nazwa) VALUES('" + zestaw + "');");
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
