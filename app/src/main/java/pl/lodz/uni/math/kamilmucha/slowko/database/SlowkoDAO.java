package pl.lodz.uni.math.kamilmucha.slowko.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlowkoDAO {
    private DbManager dbManager;
    private Random randomGenerator;

    public  SlowkoDAO(Context context){

        dbManager= new DbManager(context);
        randomGenerator = new Random();
    }

    public void insertSlowko(final Slowko slowko){

        ContentValues values = new ContentValues();
        values.put("slowko", slowko.getSlowko());
        values.put("tlumaczenie", slowko.getTlumaczenie());
        values.put("czy_umie", false);

        dbManager.getWritableDatabase().insert(Slowka.TABLE_NAME, null, values);
    }

    public Slowko getSlowkoById(final int id) {
        Cursor cursor = dbManager.getReadableDatabase().rawQuery("select * from " + Slowka.TABLE_NAME + " where _id  = " + id, null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            return mapCursorToSlowko(cursor);
        }
        return null;
    }

    public void deleteSlowkoById(final Integer id) {
        dbManager.getWritableDatabase().delete(Slowka.TABLE_NAME,
                " " + "_id" + " = ? ",
                new String[]{id.toString()}
        );
    }


    public List getAllSlowkas() {
        Cursor cursor = dbManager.getReadableDatabase().query(Slowka.TABLE_NAME,
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

    public Slowko getRandomSlowko(){
        Cursor cursor = dbManager.getReadableDatabase()
                .rawQuery("SELECT * FROM " + Slowka.TABLE_NAME  + " where czy_umie=0",null);

        List results = new ArrayList<Slowko>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                results.add(mapCursorToSlowko(cursor));
            }
        }

        int index = randomGenerator.nextInt(results.size());

        Slowko slowko;
        slowko = (Slowko) results.get(index);
        return slowko;
    }

    public void updateCzyUmie(final Slowko slowko, boolean czyUmie){
        ContentValues contentValues = new ContentValues();
        contentValues.put("slowko", slowko.getSlowko());
        contentValues.put("tlumaczenie", slowko.getTlumaczenie());
        contentValues.put("czy_umie", czyUmie);

        dbManager.getWritableDatabase().update(Slowka.TABLE_NAME,
                contentValues,
                 "_id = ? ",
                new String[]{slowko.get_id().toString()}
        );
    }

    public void updateResetujWszystkieCzyUmie(){
        Cursor cursor = dbManager.getReadableDatabase().query(Slowka.TABLE_NAME,
                new String[]{"_id", "slowko", "tlumaczenie", "czy_umie"},
                null, null, null, null, null
        );


        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                updateCzyUmie(mapCursorToSlowko(cursor),false);
            }
        }

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
