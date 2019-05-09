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
    }

    public List getAllZestaws() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query("ZESTAWY",
                new String[]{"_id", "nazwa"},
                null, null, null, null, null
        );

        List results = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToZestaw(cursor));
            }
        }
        return results;
    }

    private Zestaw mapCursorToZestaw(Cursor cursor) {
        int idColumnId = cursor.getColumnIndex("_id");
        int nazwaColumnId = cursor.getColumnIndex("nazwa");

        Zestaw zestaw = new Zestaw(cursor.getInt(idColumnId), cursor.getString(nazwaColumnId));
        return zestaw;
    }
}
