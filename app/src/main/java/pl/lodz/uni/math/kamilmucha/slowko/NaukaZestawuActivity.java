package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;

public class NaukaZestawuActivity extends AppCompatActivity {

    private int idZestawu;
    private int pozostaloDoNauki;
    private Slowko slowko;

    private Button buttonSprawdz;
    private Button buttonUmiem;
    private Button buttonNieUmiem;

    private TextView textViewSlowko;
    private TextView textViewPodpowiedz;
    private TextView textViewWszystkich;
    private TextView textViewPozostalo;

    private EditText editTextWpisane;

    private SlowkoDAO slowkoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauka_zestawu);

        buttonSprawdz = findViewById(R.id.buttonSprawdzWNauce);
        buttonUmiem = findViewById(R.id.buttonUmiem);
        buttonNieUmiem = findViewById(R.id.buttonNieUmiem);
        textViewSlowko = findViewById(R.id.textViewSlowkoDoNauki);
        textViewPodpowiedz = findViewById(R.id.textViewPodpowiedz);
        textViewWszystkich = findViewById(R.id.textViewWszystkich);
        textViewPozostalo = findViewById(R.id.textViewPozostalo);
        editTextWpisane = findViewById(R.id.editTextWpisaneSlowko);

        Intent intent = getIntent();
        idZestawu = intent.getIntExtra("idZestawu", 1);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);
        slowkoDAO = new SlowkoDAO();
        refreshIle();
        getRandomSlowko();

        if(pozostaloDoNauki>0) {

        } else {
            textViewSlowko.setText("Zaliczone!");
            textViewSlowko.setBackgroundColor(Color.GREEN);
            editTextWpisane.setVisibility(View.INVISIBLE);
            buttonSprawdz.setVisibility(View.INVISIBLE);
        }



        buttonSprawdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSprawdzClicked();
            }
        });

        buttonNieUmiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNieUmiemClicked();
            }
        });

        buttonUmiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonUmiemClicked();
            }
        });
    }

    private void buttonUmiemClicked() {
        slowkoDAO.updateCzyUmie(slowko,true);
        hideButtons();
        getRandomSlowko();
    }

    private void refreshIle() {
        int wszystkie = slowkoDAO.getCount(idZestawu);
        int pozostalo = slowkoDAO.getCountIleUmiesz(idZestawu);
        pozostaloDoNauki = wszystkie-pozostalo;

        textViewWszystkich.setText(String.valueOf(wszystkie));
        textViewPozostalo.setText(String.valueOf(pozostaloDoNauki));
    }

    private void buttonNieUmiemClicked() {
        hideButtons();
        getRandomSlowko();
    }

    private void hideButtons() {
        buttonSprawdz.setVisibility(View.VISIBLE);
        buttonUmiem.setVisibility(View.INVISIBLE);
        buttonNieUmiem.setVisibility(View.INVISIBLE);
        textViewPodpowiedz.setVisibility(View.INVISIBLE);
        editTextWpisane.setEnabled(true);
        editTextWpisane.setText("");
    }

    private void showButtons() {
        buttonSprawdz.setVisibility(View.INVISIBLE);
        buttonUmiem.setVisibility(View.VISIBLE);
        buttonNieUmiem.setVisibility(View.VISIBLE);
        textViewPodpowiedz.setVisibility(View.VISIBLE);
        editTextWpisane.setEnabled(false);
    }

    private void buttonSprawdzClicked() {
        if(editTextWpisane.getText().toString().equals(slowko.getSlowko())) {
            slowkoDAO.updateCzyUmie(slowko,true);
            editTextWpisane.setText("");
            getRandomSlowko();
        }
        else {
            showButtons();
        }
    }

    private void getRandomSlowko() {
        refreshIle();
        if(pozostaloDoNauki>0) {
            slowko = slowkoDAO.getRandomSlowko(idZestawu);
            textViewSlowko.setText(slowko.getTlumaczenie());
            textViewPodpowiedz.setText(slowko.getSlowko());
        } else {
            textViewSlowko.setText("Zaliczone!");
            textViewSlowko.setBackgroundColor(Color.GREEN);
            editTextWpisane.setVisibility(View.INVISIBLE);
            buttonSprawdz.setVisibility(View.INVISIBLE);
        }
    }
}
