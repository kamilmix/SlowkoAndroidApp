package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import pl.lodz.uni.math.kamilmucha.slowko.adapters.SlowkaWZestawieAdapter;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;
import pl.lodz.uni.math.kamilmucha.slowko.dialogs.DodajSlowkoDialog;
import pl.lodz.uni.math.kamilmucha.slowko.dialogs.EdytujSlowkoDialog;

public class ZestawSlowekActivity extends AppCompatActivity implements DodajSlowkoDialog.DodajDialogListener, EdytujSlowkoDialog.EdytujSlowkoListener {
    private SlowkoDAO slowkoDAO;
    private Button buttonDodaj;
    private Button buttonNauka;
    private TextView przekazany;


    private ArrayList<Slowko> slowka;

    private SlowkaWZestawieAdapter slowkaWZestawieAdapter;

    private int idZestawu;
    private String nazwaZestawu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestaw_slowek);

        buttonDodaj = findViewById(R.id.buttonOtworzDodaj);
        buttonNauka = findViewById(R.id.buttonNaukaWZestawie);
        przekazany = findViewById(R.id.textViewPrzekazany);

        Intent intent = getIntent();
        idZestawu = intent.getIntExtra("idZestawu", 1);
        nazwaZestawu = intent.getStringExtra("nazwaZestawu");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);
        slowkoDAO = new SlowkoDAO();
        slowka = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas(idZestawu);

        przekazany.setText(nazwaZestawu);
        
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewSlowkaWZestawie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        slowkaWZestawieAdapter = new SlowkaWZestawieAdapter(slowka, recyclerView, this);
        recyclerView.setAdapter(slowkaWZestawieAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh();
    }

    public void onClickButtonOtworzDialog(View view) {
        DodajSlowkoDialog dodajDialog = new DodajSlowkoDialog();
        dodajDialog.show(getSupportFragmentManager(), "dialog dodaj slowko");
    }

    public void onClickButtonResetujWszystkie(View view) {
        slowkoDAO.updateResetujWszystkieCzyUmie(idZestawu);
        refresh();
    }

    @Override
    public void przeslijSlowko(String slowko, String tlumaczenie) {
        slowkoDAO.insertSlowko(new Slowko(slowko, tlumaczenie), idZestawu);
        ArrayList<Slowko> allSlowkasList = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas(idZestawu);
        slowka.add(allSlowkasList.get(allSlowkasList.size() - 1));
        slowkaWZestawieAdapter.notifyDataSetChanged();
    }

    @Override
    public void przeslijEdytowaneSlowko(Slowko slowko) {
        refresh();
    }

    public void onClickNaukaWZestawie(View view) {
        Intent intent = new Intent(this, NaukaZestawuActivity.class);
        intent.putExtra("idZestawu", idZestawu);
        startActivity(intent);
    }

    private void refresh() {
        ArrayList<Slowko> slowkaNowe = new ArrayList<Slowko>();
        slowkaNowe = (ArrayList<Slowko>) slowkoDAO.getAllSlowkas(idZestawu);
        Collections.copy(slowka, slowkaNowe);
        slowkaWZestawieAdapter.notifyDataSetChanged();
    }

    private void showShortToast(String text) {
        Toast.makeText(ZestawSlowekActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    public void onClickButtonUpload(View view) {
        new UploadTask().execute();
    }

    private class UploadTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            HttpURLConnection httpURLConnection = null;

            try {
                URL restApiEndpoint = new URL(MainActivity.API_URL);
                httpURLConnection = (HttpURLConnection) restApiEndpoint.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");

                JSONObject zestawJSON = new JSONObject();
                zestawJSON.put("name", nazwaZestawu);

                JSONArray slowkaJSON = new JSONArray();

                for (Slowko slowko : slowka) {
                    JSONObject slowkoJSON = new JSONObject();
                    slowkoJSON.put("slowko", slowko.getSlowko());
                    slowkoJSON.put("tlumaczenie", slowko.getTlumaczenie());
                    slowkaJSON.put(slowkoJSON);
                }

                JSONObject allValuesJSON = new JSONObject();
                allValuesJSON.put("zestaw", zestawJSON);
                allValuesJSON.put("slowka", slowkaJSON);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getOutputStream().write(allValuesJSON.toString().getBytes());

                return httpURLConnection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
                showShortToast("ERROR");
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            if (responseCode == 200) {
                showShortToast("Wysłano zestaw poprawnie!");
            } else {
                showShortToast("Coś poszło nie tak!");
            }
        }
    }


}
