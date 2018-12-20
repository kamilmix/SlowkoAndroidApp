package pl.lodz.uni.math.kamilmucha.slowko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DbManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.SlowkoDAO;

public class NaukaActivity extends AppCompatActivity {

    private SlowkoDAO slowkoDAO;
    private TextView textView;
    private TextView textViewCzyDobrze;
    private EditText editTextWpisaneTlumaczenie;
    private Slowko wylosowaneSlowko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauka);
        slowkoDAO = new SlowkoDAO(this);

        textView = findViewById(R.id.textView);
        textViewCzyDobrze = findViewById(R.id.textViewCzyDobrze);
        editTextWpisaneTlumaczenie = findViewById(R.id.editText);
        losujSlowkoZBazy();
    }

    private void losujSlowkoZBazy() {
        wylosowaneSlowko= slowkoDAO.getRandomSlowko();
        textView.setText(wylosowaneSlowko.getTlumaczenie());
    }

    public void onClickLosuj(View v){
        String wylosowane = wylosowaneSlowko.getSlowko();
        String wpisane = editTextWpisaneTlumaczenie.getText().toString();

        if(wylosowane.equals(wpisane)){
            textViewCzyDobrze.setText("DOBRZE");
            editTextWpisaneTlumaczenie.setText("");

            losujSlowkoZBazy();
        }
        else{
            textViewCzyDobrze.setText("ZLE");
        }
    }
}
