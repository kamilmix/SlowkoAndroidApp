package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;

public class ZestawyActivity extends AppCompatActivity implements DodajDialog.DodajDialogListener {

    private SlowkoDAO slowkoDAO;
    private ListView listView;
    private ArrayAdapter<Slowko> adapter;
    private Button buttonDodaj;
    private TextView przekazany;

    private int idZestawu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestawy);
        listView = findViewById(R.id.listView);
        buttonDodaj = findViewById(R.id.buttonOtworzDodaj);
        przekazany = findViewById(R.id.textViewPrzekazany);

        Intent intent = getIntent();
        idZestawu = intent.getIntExtra("idZestawu", 1);
        String nazwaZestawu = intent.getStringExtra("nazwaZestawu");

        slowkoDAO = new SlowkoDAO(this);
        reloadSlowkasList();

        przekazany.setText(nazwaZestawu);
    }


    private void reloadSlowkasList() {
        // pobieramy z bazy danych listę slowek
        final ArrayList<Slowko> allSlowkasList = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas(idZestawu);

        SlowkoListAdapter slowkoListAdapter = new SlowkoListAdapter(this, R.layout.slowko_layout, allSlowkasList);
        listView.setAdapter(slowkoListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Slowko slowkoDoUsuniecia = allSlowkasList.get(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(ZestawyActivity.this);
                alert.setTitle("Usuń słówko");
                alert.setMessage("Czy na pewno chcesz usunąć? \n" + slowkoDoUsuniecia.getSlowko() + " \t " + slowkoDoUsuniecia.getTlumaczenie());
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        removeSlowko(slowkoDoUsuniecia);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }

    private void removeSlowko(Slowko id) {
        slowkoDAO.deleteSlowkoById(id.get_id());
        reloadSlowkasList();
    }

    protected void onClickButtonOtworzDialog(View view) {
        DodajDialog dodajDialog = new DodajDialog();
        dodajDialog.show(getSupportFragmentManager(), "dialog dodaj");
    }

    protected void onClickButtonResetujWszystkie(View view) {
        slowkoDAO.updateResetujWszystkieCzyUmie();
        reloadSlowkasList();
    }

    @Override
    public void przeslijSlowko(String slowko, String tlumaczenie) {
        slowkoDAO.insertSlowko(new Slowko(slowko, tlumaczenie),idZestawu);
       // List<Slowko> allSlowkasList = slowkoDAO.getAllSlowkas();
        reloadSlowkasList();
    }

}
