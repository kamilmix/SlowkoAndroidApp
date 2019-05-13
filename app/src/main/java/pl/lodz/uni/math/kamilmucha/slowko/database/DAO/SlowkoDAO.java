package pl.lodz.uni.math.kamilmucha.slowko.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.Slowka;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class SlowkoDAO {
    private DatabaseHelper databaseHelper;
    private Random randomGenerator;

    public SlowkoDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        randomGenerator = new Random();
    }

    public void insertSlowko(final Slowko slowko) {
        ContentValues values = new ContentValues();
        values.put("slowko", slowko.getSlowko());
        values.put("tlumaczenie", slowko.getTlumaczenie());
        values.put("czy_umie", false);

        databaseHelper.getWritableDatabase().insert(Slowka.TABLE_NAME, null, values);
    }


    public void insertSlowko(final Slowko slowko, int idZestawu) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("slowko", slowko.getSlowko());
        values.put("tlumaczenie", slowko.getTlumaczenie());
        values.put("czy_umie", false);
        values.put("id_zestawu", idZestawu);

        db.insert(Slowka.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public Slowko getSlowkoById(final int id) {
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("select * from " + Slowka.TABLE_NAME + " where _id  = " + id, null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            return mapCursorToSlowko(cursor);
        }
        return null;
    }

    public void deleteSlowkoById(final Integer id) {
        databaseHelper.getWritableDatabase().delete(Slowka.TABLE_NAME,
                " " + "_id" + " = ? ",
                new String[]{id.toString()}
        );
    }


    public List getAllSlowkas() {
        Cursor cursor = databaseHelper.getReadableDatabase().query(Slowka.TABLE_NAME,
                new String[]{"_id", "slowko", "tlumaczenie", "czy_umie"},
                null, null, null, null, null
        );

        List results = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToSlowko(cursor));
            }
        }
        return results;
    }

    public List getAllSlowkas(int idZestawu) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query(Slowka.TABLE_NAME,
                new String[]{"_id", "slowko", "tlumaczenie", "czy_umie"},
                "id_zestawu=" + idZestawu, null, null, null, null
        );

        List results = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToSlowko(cursor));
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        return results;
    }

    public Slowko getRandomSlowko() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db
                .rawQuery("SELECT * FROM " + Slowka.TABLE_NAME + " where czy_umie=0", null);

        List results = new ArrayList<Slowko>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToSlowko(cursor));
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        int index = randomGenerator.nextInt(results.size());

        Slowko slowko;
        slowko = (Slowko) results.get(index);
        return slowko;
    }

    public void updateCzyUmie(final Slowko slowko, boolean czyUmie) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("slowko", slowko.getSlowko());
        contentValues.put("tlumaczenie", slowko.getTlumaczenie());
        contentValues.put("czy_umie", czyUmie);

        db.update(Slowka.TABLE_NAME,
                contentValues,
                "_id = ? ",
                new String[]{slowko.get_id().toString()}
        );
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateResetujWszystkieCzyUmie() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query(Slowka.TABLE_NAME,
                new String[]{"_id", "slowko", "tlumaczenie", "czy_umie"},
                null, null, null, null, null
        );


        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                updateCzyUmie(mapCursorToSlowko(cursor), false);
            }
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    public int getIlePozostalo() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db
                .rawQuery("SELECT * FROM " + Slowka.TABLE_NAME + " where czy_umie=0", null);

        int counter = 0;

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                counter++;
            }
        }

        DatabaseManager.getInstance().closeDatabase();

        return counter;
    }


    private Slowko mapCursorToSlowko(final Cursor cursor) {
        int idColumnId = cursor.getColumnIndex("_id");
        int slowkoColumnId = cursor.getColumnIndex("slowko");
        int tlumaczenieColumnId = cursor.getColumnIndex("tlumaczenie");
        int czyUmieColumnId = cursor.getColumnIndex("czy_umie");

        Slowko slowko = new Slowko();
        slowko.set_id(cursor.getInt(idColumnId));
        slowko.setSlowko(cursor.getString(slowkoColumnId));
        slowko.setTlumaczenie(cursor.getString(tlumaczenieColumnId));
        slowko.setCzyUmie(cursor.getInt(czyUmieColumnId));

        return slowko;
    }
}

