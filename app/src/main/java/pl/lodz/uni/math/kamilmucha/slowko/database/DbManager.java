package pl.lodz.uni.math.kamilmucha.slowko.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DbManager extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Database";
        public static final String KEY_ID = "_id";
        public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";


        private static final String CREATE_TABLE_ZESTAWY = "CREATE TABLE ZESTAWY (" + KEY_ID  + " " + ID_OPTIONS  + ", " + "nazwa TEXT NOT NULL)";

    /*    private static final String CREATE_TABLE_SLOWKA = "CREATE TABLE SLOWKA (" +
    KEY_ID  + " " + ID_OPTIONS  + ", " +
            "slowko TEXT NOT NULL, " +
            "tlumaczenie TEXT NOT NULL, " +
            "czy_umie BOOLEAN, " +
            "id_zestawu INTEGER," +
            "FOREIGN KEY (id_zestawu) REFERENCES ZESTAWY (_id)" +
            " )";
*/
    private static final String CREATE_TABLE_SLOWKA = "CREATE TABLE SLOWKA (" +
            KEY_ID  + " " + ID_OPTIONS  + ", " +
            "slowko TEXT NOT NULL, " +
            "tlumaczenie TEXT NOT NULL, " +
            "czy_umie BOOLEAN " +
            " )";

        private static final String INSERT_DATA_TO_ZESTAWY = "INSERT INTO ZESTAWY (nazwa) VALUES(kolory);";
        private static final String INSERT_DATA_TO_SLOWKA =
                "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES(red, czerwony, 0, 1);";
        //  "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES(blue, niebieski, 0, 1);" +
        //  "INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES(white, bialy, 0, 1);";



        private static final String DROP_TABLE_ZESTAWY ="DROP TABLE IF EXISTS ZESTAWY" ;
        private static final String DROP_TABLE_SLOWKA="DROP TABLE IF EXISTS SLOWKA" ;

        public DbManager(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_ZESTAWY);
            db.execSQL(CREATE_TABLE_SLOWKA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_SLOWKA);
            db.execSQL(DROP_TABLE_ZESTAWY);
            db.execSQL(CREATE_TABLE_ZESTAWY);
            db.execSQL(CREATE_TABLE_SLOWKA);
        }

        public String getRandom()
        {
            String selectQuery = "SELECT * FROM ZESTAWY LIMIT 1";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,null);

            return cursor.getString(1);
        }

        public void addZestaw(String string){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nazwa",string);
            db.insert("ZESTAWY", null, values);
        }

        public void addSlowko(String slowko, String tlumaczenie, Integer idZestawu){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("slowko", slowko);
            values.put("tlumaczenie", tlumaczenie);
            values.put("id_zestawu", idZestawu);
            db.insert("SLOWKA", null, values);
        }

        public ArrayList<String> getAllToArray() {
            ArrayList<String> list = new ArrayList<>();

            String selectQuery = "SELECT * FROM ZESTAWY";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery,null);

            while(cursor.moveToNext()) {
                list.add(cursor.getString(1));
            }
            cursor.close();
            return list;
        }
    }
