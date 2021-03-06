package pl.lodz.uni.math.kamilmucha.slowko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;

public class NaukaActivity extends AppCompatActivity {

    private SlowkoDAO slowkoDAO;
    private TextView textView;
    private TextView textViewCzyDobrze;
    private EditText editTextWpisaneTlumaczenie;
    private Slowko wylosowaneSlowko;
    private Button buttonLosuj;
    private String wylosowane;
    private String wpisane;
    private int ilePozostalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauka);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);

        slowkoDAO = new SlowkoDAO();

        textView = findViewById(R.id.textView);
        textViewCzyDobrze = findViewById(R.id.textViewCzyDobrze);
        editTextWpisaneTlumaczenie = findViewById(R.id.editText);
        buttonLosuj = findViewById(R.id.buttonLosuj);
        losujSlowkoZBazy();
    }

    private void losujSlowkoZBazy() {
        ilePozostalo = slowkoDAO.getIlePozostalo();
        if (ilePozostalo > 0) {
            wylosowaneSlowko = slowkoDAO.getRandomSlowko();
            textView.setText(wylosowaneSlowko.getTlumaczenie());

        } else {
            textView.setText("");
            //buttonLosuj.setClickable(false);
            editTextWpisaneTlumaczenie.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Brak słówek do nauki", Toast.LENGTH_SHORT).show();
            wylosowaneSlowko = new Slowko("XXXX", "XXXX");

        }
    }

    public void onClickLosuj(View v) {

        wylosowane = wylosowaneSlowko.getSlowko();
        wpisane = editTextWpisaneTlumaczenie.getText().toString();

        if (wylosowane.equals(wpisane)) {
            textViewCzyDobrze.setText("DOBRZE");
            editTextWpisaneTlumaczenie.setText("");
            slowkoDAO.updateCzyUmie(wylosowaneSlowko, true);

            losujSlowkoZBazy();
        } else {
            if (ilePozostalo > 0) {
                textViewCzyDobrze.setText("ZLE");
            } else {
                textViewCzyDobrze.setText("");
            }

        }
    }
}
