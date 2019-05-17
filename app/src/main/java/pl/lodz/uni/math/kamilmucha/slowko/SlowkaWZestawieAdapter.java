package pl.lodz.uni.math.kamilmucha.slowko;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;

public class SlowkaWZestawieAdapter extends RecyclerView.Adapter {
   private ArrayList<Slowko> slowka;
   private RecyclerView recyclerView;

   private SlowkoDAO slowkoDAO;

   private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSlowko;
        public TextView textViewTlumaczenie;
        public ImageView checkCzyUmie;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           textViewSlowko = itemView.findViewById(R.id.slowko_layout_slowko);
           textViewTlumaczenie = itemView.findViewById(R.id.slowko_layout_tlumaczenie);
           checkCzyUmie = itemView.findViewById(R.id.imageViewSlowkoCheck);
       }
   }

   public SlowkaWZestawieAdapter(ArrayList<Slowko> slowka, RecyclerView recyclerView) {
       this.slowka = slowka;
       this.recyclerView = recyclerView;
   }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slowko_layout, viewGroup, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
        DatabaseManager.initializeInstance(databaseHelper);
        slowkoDAO = new SlowkoDAO();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Slowko slowko = slowka.get(i);
        ((MyViewHolder) viewHolder).textViewSlowko.setText(slowko.getSlowko());
        ((MyViewHolder) viewHolder).textViewTlumaczenie.setText(slowko.getTlumaczenie());
        if(slowko.isCzyUmie()){
            ((MyViewHolder) viewHolder).checkCzyUmie.setVisibility(View.VISIBLE);
       // viewHolder.itemView.setBackgroundColor(Color.parseColor("#E8FFEE"));
        }
    }

    @Override
    public int getItemCount() {
        return slowka.size();
    }
}
