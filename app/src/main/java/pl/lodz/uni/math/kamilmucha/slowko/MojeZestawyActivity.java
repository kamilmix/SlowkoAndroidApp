package pl.lodz.uni.math.kamilmucha.slowko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.ZestawDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class MojeZestawyActivity extends AppCompatActivity {

    private static DatabaseHelper databaseHelper;

    private Button buttonDodaj;
    private EditText editTextNazwa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moje_zestawy);

        databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);

        final SlowkoDAO slowkoDAO = new SlowkoDAO(this);
        final ZestawDAO zestawDAO = new ZestawDAO();

        buttonDodaj = findViewById(R.id.buttonDodajZestaw);
        editTextNazwa = findViewById(R.id.editTextNazwaZestawu);
        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zestaw zestaw = new Zestaw();
                zestaw.setNazwa(editTextNazwa.getText().toString());
                zestawDAO.insertZestaw(zestaw);
            }
        });



        ArrayList<Zestaw> zestawy = (ArrayList<Zestaw>) zestawDAO.getAllZestaws();

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewZestawy);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new ZestawyAdapter(zestawy, recyclerView));



    }
}
