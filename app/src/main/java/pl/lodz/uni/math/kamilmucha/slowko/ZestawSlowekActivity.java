package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;

public class ZestawSlowekActivity extends AppCompatActivity implements DodajSlowkoDialog.DodajDialogListener {
    private SlowkoDAO slowkoDAO;
    private Button buttonDodaj;
    private TextView przekazany;

    private ArrayList<Slowko> slowka;

    private SlowkaWZestawieAdapter slowkaWZestawieAdapter;

    private int idZestawu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestaw_slowek);

        buttonDodaj = findViewById(R.id.buttonOtworzDodaj);
        przekazany = findViewById(R.id.textViewPrzekazany);

        Intent intent = getIntent();
        idZestawu = intent.getIntExtra("idZestawu", 1);
        String nazwaZestawu = intent.getStringExtra("nazwaZestawu");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);
        slowkoDAO = new SlowkoDAO();
        slowka = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas(idZestawu);

        przekazany.setText(nazwaZestawu);

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewSlowkaWZestawie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        slowkaWZestawieAdapter = new SlowkaWZestawieAdapter(slowka, recyclerView);
        recyclerView.setAdapter(slowkaWZestawieAdapter);
    }

    private void removeSlowko(Slowko id) {
        slowkoDAO.deleteSlowkoById(id.get_id());
        //  reloadSlowkasList();
    }

    protected void onClickButtonOtworzDialog(View view) {
        DodajSlowkoDialog dodajDialog = new DodajSlowkoDialog();
        dodajDialog.show(getSupportFragmentManager(), "dialog dodaj");
    }

    protected void onClickButtonResetujWszystkie(View view) {
        slowkoDAO.updateResetujWszystkieCzyUmie();
        //  reloadSlowkasList();
    }

    @Override
    public void przeslijSlowko(String slowko, String tlumaczenie) {
        slowkoDAO.insertSlowko(new Slowko(slowko, tlumaczenie), idZestawu);
        ArrayList<Slowko> allSlowkasList = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas();
        slowka.add(allSlowkasList.get(allSlowkasList.size() - 1));
        slowkaWZestawieAdapter.notifyDataSetChanged();
    }

}
