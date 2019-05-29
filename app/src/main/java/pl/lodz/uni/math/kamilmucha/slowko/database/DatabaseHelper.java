package pl.lodz.uni.math.kamilmucha.slowko.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 10;
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

    private static final String[] nazwyZestawow = {"kolory", "zwierzęta", "owoce", "dni tygodnia"};
    private static final HashMap<String, String> nazwyKolorow = new HashMap<>();
    private static final HashMap<String, String> nazwyZwierzat = new HashMap<>();
    private static final HashMap<String, String> nazwyDni = new HashMap<>();
    private static final HashMap<String, String> nazwyOwocow = new HashMap<>();

    static {
        nazwyKolorow.put("red", "czerwony");
        nazwyKolorow.put("blue", "niebieski");
        nazwyKolorow.put("black", "czarny");
        nazwyKolorow.put("white", "biały");
        nazwyKolorow.put("yellow", "żółty");
        nazwyKolorow.put("orange", "pomarańczowy");
        nazwyKolorow.put("pink", "różowy");
        nazwyKolorow.put("purple", "fioletowy");
        nazwyKolorow.put("brown", "brązowy");
        nazwyKolorow.put("grey", "szary");
        nazwyKolorow.put("beige", "beżowy");

        nazwyZwierzat.put("cat", "kot");
        nazwyZwierzat.put("ant", "mrówka");
        nazwyZwierzat.put("chicken", "kurczak");
        nazwyZwierzat.put("eagle", "orzeł");
        nazwyZwierzat.put("bee", "pszczoła");
        nazwyZwierzat.put("cow", "krowa");
        nazwyZwierzat.put("elephant", "słoń");
        nazwyZwierzat.put("bear", "niedźwiedź");
        nazwyZwierzat.put("fly", "mucha");
        nazwyZwierzat.put("bull", "byk");
        nazwyZwierzat.put("frog", "żaba");
        nazwyZwierzat.put("butterfly", "motyl");
        nazwyZwierzat.put("dolphin", "delin");
        nazwyZwierzat.put("donkey", "osioł");
        nazwyZwierzat.put("sheep", "owca");
        nazwyZwierzat.put("rabbit", "królik");
        nazwyZwierzat.put("goat", "koza");
        nazwyZwierzat.put("wolf", "wilk");
        nazwyZwierzat.put("penguin", "pingwin");

        nazwyDni.put("monday", "poniedziałek");
        nazwyDni.put("tuesday", "wtorek");
        nazwyDni.put("wednesday", "środa");
        nazwyDni.put("thursday", "czwartek");
        nazwyDni.put("friday", "piątek");
        nazwyDni.put("saturday", "sobota");
        nazwyDni.put("sunday", "niedziela");

        nazwyOwocow.put("redcurrant", "porzeczka czerwona");
        nazwyOwocow.put("blackcurrant", "porzeczka czarna");
        nazwyOwocow.put("gooseberry", "agrest");
        nazwyOwocow.put("grape", "winogrono");
        nazwyOwocow.put("blueberry", "jagoda czarna");
        nazwyOwocow.put("bilberry", "borówka czarna");
        nazwyOwocow.put("cranberry", "żurawina");
        nazwyOwocow.put("raspberry", "malina");
        nazwyOwocow.put("blackberry", "jeżyna");
        nazwyOwocow.put("wild strawberry", "poziomka");
        nazwyOwocow.put("plum", "śliwka");
        nazwyOwocow.put("peach", "brzoskwinia");
        nazwyOwocow.put("nectarine", "nektaryna");
        nazwyOwocow.put("apricot", "morela");
        nazwyOwocow.put("cherry", "wiśnia");
        nazwyOwocow.put("pear", "gruszka");
        nazwyOwocow.put("apple", "jabłko");
        nazwyOwocow.put("lemon", "cytryna");
        nazwyOwocow.put("lime", "limonka");
        nazwyOwocow.put("orange", "pomarańcza");
        nazwyOwocow.put("grapefruit", "grejfrut");
        nazwyOwocow.put("watermelon", "arbuz");
        nazwyOwocow.put("pineaple", "ananas");
        nazwyOwocow.put("tangerine", "mandarynka");
        nazwyOwocow.put("papaya", "papaja");
    }

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
        populateSlowka(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_SLOWKA);
        db.execSQL(DROP_TABLE_ZESTAWY);

        db.execSQL(CREATE_TABLE_ZESTAWY);
        db.execSQL(CREATE_TABLE_SLOWKA);

        populateZestawy(db);
        populateSlowka(db);
    }

    private void populateZestawy(SQLiteDatabase db) {
        for (String zestaw : nazwyZestawow) {
            db.execSQL("INSERT INTO ZESTAWY (nazwa) VALUES('" + zestaw + "');");
        }
    }

    private void populateSlowka(SQLiteDatabase db) {
        for (HashMap.Entry<String, String> slowko : nazwyKolorow.entrySet()) {
            db.execSQL("INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES ('" + slowko.getKey() + "', '" + slowko.getValue() + "', 0 , 1)");
        }

        for (HashMap.Entry<String, String> slowko : nazwyZwierzat.entrySet()) {
            db.execSQL("INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES ('" + slowko.getKey() + "', '" + slowko.getValue() + "', 0 , 2)");
        }

        for (HashMap.Entry<String, String> slowko : nazwyDni.entrySet()) {
            db.execSQL("INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES ('" + slowko.getKey() + "', '" + slowko.getValue() + "', 0 , 4)");
        }

        for (HashMap.Entry<String, String> slowko : nazwyOwocow.entrySet()) {
            db.execSQL("INSERT INTO SLOWKA (slowko, tlumaczenie, czy_umie, id_zestawu) VALUES ('" + slowko.getKey() + "', '" + slowko.getValue() + "', 0 , 3)");
        }
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
