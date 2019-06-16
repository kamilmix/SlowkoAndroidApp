package pl.lodz.uni.math.kamilmucha.slowko;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import pl.lodz.uni.math.kamilmucha.slowko.adapters.PobierzZestawAdapter;
import pl.lodz.uni.math.kamilmucha.slowko.adapters.WybierzZestawDoNaukiAdapter;
import pl.lodz.uni.math.kamilmucha.slowko.adapters.ZestawyAdapter;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class PobierzZestawyActivity extends AppCompatActivity {

    private ArrayList<Zestaw> zestawy;
    private ZestawyAdapter zestawyAdapter;
    private ProgressBar progressBar;
    private PobierzZestawAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pobierz_zestawy);

        progressBar = findViewById(R.id.progressBarPobierz);
        progressBar.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewPobierzZestawy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        zestawy = new ArrayList<>();
        adapter = new PobierzZestawAdapter(zestawy, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    public void onClickOdswiez(View view) {
        new GetTask().execute();
    }

    private class GetTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            int response = 0;
            try {
                URL restApiEndpoint = new URL(MainActivity.API_URL);
                httpURLConnection = (HttpURLConnection) restApiEndpoint.openConnection();
                response = httpURLConnection.getResponseCode();
                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream responseBody = httpURLConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    int data = responseBodyReader.read();
                    while (data != -1) {
                        stringBuilder.append((char) data);
                        data = responseBodyReader.read();
                    }
                    return stringBuilder.toString();
                }

            } catch (Exception e) {
                //    Toast.makeText(PobierzZestawyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            String r = String.valueOf(response);
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String dataFromGet) {
            String a = dataFromGet;
            try {
                Log.d("ABD", dataFromGet);
                JSONArray jsonArray = new JSONArray(dataFromGet);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    int id = rec.getInt("id");
                    String nazwa = rec.getString("name");
                    Zestaw zestaw = new Zestaw(id, nazwa);
                    zestawy.add(zestaw);
                }

                //  String title = "TITLE: " + jsonObject.getString("title");
                // textViewTitle.setText(title);
                //  String body = jsonObject.getString("body");
                //  textViewBody.setText(body);

            } catch (Exception e) {
                //  Toast.makeText(PobierzZestawyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}
