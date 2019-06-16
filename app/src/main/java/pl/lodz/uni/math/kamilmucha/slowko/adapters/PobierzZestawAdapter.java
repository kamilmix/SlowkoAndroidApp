package pl.lodz.uni.math.kamilmucha.slowko.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.MainActivity;
import pl.lodz.uni.math.kamilmucha.slowko.MojeZestawyActivity;
import pl.lodz.uni.math.kamilmucha.slowko.R;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.ZestawDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class PobierzZestawAdapter extends RecyclerView.Adapter {

    private ArrayList<Zestaw> zestawy;
    private RecyclerView recyclerView;

    private ZestawDAO zestawDAO;
    private SlowkoDAO slowkoDAO;

    private Context context;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nazwaZestawu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nazwaZestawu = itemView.findViewById(R.id.textViewNazwaZestawuDoPobrania);

        }
    }

    public PobierzZestawAdapter(ArrayList<Zestaw> zestawy, RecyclerView recyclerView) {
        this.zestawy = zestawy;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zestaw_wybor_zestawu_do_pobrania, viewGroup, false);

        context = view.getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DatabaseManager.initializeInstance(databaseHelper);

        zestawDAO = new ZestawDAO();
        slowkoDAO = new SlowkoDAO();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                int idZestawu = zestawy.get(position).getId();
                zestawDAO.insertZestaw(zestawy.get(position));

                new GetSlowkaTask().execute(idZestawu);
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MyViewHolder) viewHolder).nazwaZestawu.setText(zestawy.get(i).getNazwa());
    }

    @Override
    public int getItemCount() {
        return zestawy.size();
    }

    private class GetSlowkaTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection httpURLConnection = null;

            try {
                URL restApiEndpoint = new URL(MainActivity.API_URL + "/" + params[0].toString());
                httpURLConnection = (HttpURLConnection) restApiEndpoint.openConnection();
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
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String dataFromGet) {
            ArrayList zestawy = zestawDAO.getAllZestaws();
            Zestaw zestaw = (Zestaw) zestawy.get(zestawy.size() - 1);
            try {
                JSONArray jsonArray = new JSONArray(dataFromGet);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject rec = jsonArray.getJSONObject(i);
                    String slowko = rec.getString("slowko");
                    String tlumaczenie = rec.getString("tlumaczenie");
                    slowkoDAO.insertSlowko(new Slowko(slowko, tlumaczenie), zestaw.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Pobrano zestaw!", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, MojeZestawyActivity.class));
        }
    }
}
