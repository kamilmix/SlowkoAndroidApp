package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNauka(View v) {
        Intent intent = new Intent(this, NaukaActivity.class);
        startActivity(intent);

    }

    public void onClickZestawy(View v) {
        Intent intent = new Intent(this, MojeZestawyActivity.class);
        startActivity(intent);
    }


    public void onClickPomoc(View v) {
        Intent intent = new Intent(this, PomocActivity.class);
        startActivity(intent);
    }
}
