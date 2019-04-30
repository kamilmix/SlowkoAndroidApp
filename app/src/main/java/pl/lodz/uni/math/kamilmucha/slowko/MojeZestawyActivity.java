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
import java.util.List;

import pl.lodz.uni.math.kamilmucha.slowko.database.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.Zestaw;

public class MojeZestawyActivity extends AppCompatActivity {

    private Button buttonDodaj;
    private EditText editTextNazwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moje_zestawy);
        final SlowkoDAO slowkoDAO = new SlowkoDAO(this);

        buttonDodaj = findViewById(R.id.buttonDodajZestaw);
        editTextNazwa = findViewById(R.id.editTextNazwaZestawu);
        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zestaw zestaw = new Zestaw();
                zestaw.setNazwa(editTextNazwa.getText().toString());
                slowkoDAO.insertZestaw(zestaw);
            }
        });



        ArrayList<Zestaw> zestawy = (ArrayList<Zestaw>) slowkoDAO.getAllZestaws();

        RecyclerView recyclerView = findViewById(R.id.RecyclerViewZestawy);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new ZestawyAdapter(zestawy, recyclerView));



    }
}
