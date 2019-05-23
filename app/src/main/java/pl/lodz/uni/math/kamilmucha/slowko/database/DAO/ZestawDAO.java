package pl.lodz.uni.math.kamilmucha.slowko.database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class ZestawDAO {

    public void insertZestaw(final Zestaw zestaw) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("nazwa", zestaw.getNazwa());

        db.insert("ZESTAWY", null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(int idZestawu){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete("ZESTAWY",
                " " + "_id" + " = ? ",
                new String[]{String.valueOf(idZestawu)}
                );

        DatabaseManager.getInstance().closeDatabase();
    }

    public void rename(int idZestawu, String nazwa){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("nazwa", nazwa);
        db.update("ZESTAWY", values, "_id=" + idZestawu, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList getAllZestaws() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query("ZESTAWY",
                new String[]{"_id", "nazwa"},
                null, null, null, null, null
        );

        ArrayList<Zestaw> results = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToZestaw(cursor));
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        return results;

    }

    public ArrayList getAllZestawsDoNauki() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db
                .rawQuery("SELECT id_zestawu AS _id, nazwa FROM SLOWKA " +
                        "INNER JOIN ZESTAWY ON ZESTAWY._id = SLOWKA.id_zestawu " +
                        "WHERE czy_umie = 0 GROUP BY id_zestawu", null);

        ArrayList<Zestaw> results = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToZestaw(cursor));
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        return results;

    }

    private Zestaw mapCursorToZestaw(Cursor cursor) {
        int idColumnId = cursor.getColumnIndex("_id");
        int nazwaColumnId = cursor.getColumnIndex("nazwa");

        return new Zestaw(cursor.getInt(idColumnId), cursor.getString(nazwaColumnId));
    }
}
