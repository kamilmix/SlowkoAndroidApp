package pl.lodz.uni.math.kamilmucha.slowko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import pl.lodz.uni.math.kamilmucha.slowko.database.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.database.SlowkoDAO;

public class ZestawyActivity extends AppCompatActivity implements DodajDialog.DodajDialogListener{

    private SlowkoDAO slowkoDAO;
    private ListView listView;
    private ArrayAdapter<Slowko> adapter;
    private Button buttonDodaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestawy);
        listView = findViewById(R.id.listView);
        buttonDodaj = findViewById(R.id.buttonOtworzDodaj);

        slowkoDAO = new SlowkoDAO(this);
        reloadSlowkasList();
    }

    private void reloadSlowkasList() {
        // pobieramy z bazy danych listę slowek
        List<Slowko> allSlowkasList =  slowkoDAO.getAllSlowkas();

        // ustawiamy adapter listy
        listView.setAdapter(new ArrayAdapter<Slowko>(this, R.layout.slowko_layout, allSlowkasList) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final View slowkoView = super.getView(position, convertView, parent);

                // po dlugim kliknięciu na słówko zostanie ono usunięte
                slowkoView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        removeSlowko(getItem(position));
                        return true;
                    }
                });
                return slowkoView;
            }
        });
    }

    private void removeSlowko(Slowko id) {
        slowkoDAO.deleteSlowkoById(id.get_id());
        reloadSlowkasList();
    }

    protected void onClickButtonOtworzDialog(View view){
        DodajDialog dodajDialog = new DodajDialog();
        dodajDialog.show(getSupportFragmentManager(),"dialog dodaj");
    }

    @Override
    public void przeslijSlowko(String slowko, String tlumaczenie) {
        slowkoDAO.insertSlowko(new Slowko(slowko, tlumaczenie));
        List<Slowko> allSlowkasList =  slowkoDAO.getAllSlowkas();
        reloadSlowkasList();
    }
}
