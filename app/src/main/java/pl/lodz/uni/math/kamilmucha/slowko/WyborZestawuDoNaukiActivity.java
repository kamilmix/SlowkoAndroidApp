package pl.lodz.uni.math.kamilmucha.slowko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.adapters.WybierzZestawDoNaukiAdapter;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.ZestawDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class WyborZestawuDoNaukiActivity extends AppCompatActivity {
    private ArrayList<Zestaw> zestawy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_zestawu_do_nauki);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        DatabaseManager.initializeInstance(databaseHelper);

        ZestawDAO zestawDAO = new ZestawDAO();
        zestawy = (ArrayList<Zestaw>) zestawDAO.getAllZestawsDoNauki();

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewWybierzZestawDoNauki);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        WybierzZestawDoNaukiAdapter adapter = new WybierzZestawDoNaukiAdapter(zestawy, recyclerView);
        recyclerView.setAdapter(adapter);
    }
}
