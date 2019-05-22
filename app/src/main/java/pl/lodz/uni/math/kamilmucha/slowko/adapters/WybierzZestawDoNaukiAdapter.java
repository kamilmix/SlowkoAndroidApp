package pl.lodz.uni.math.kamilmucha.slowko.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.NaukaZestawuActivity;
import pl.lodz.uni.math.kamilmucha.slowko.R;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

public class WybierzZestawDoNaukiAdapter extends RecyclerView.Adapter {
    private ArrayList<Zestaw> zestawy;
    private RecyclerView recyclerView;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nazwaZestawu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nazwaZestawu = itemView.findViewById(R.id.textViewNazwaZestawuDoNauki);

        }
    }

    public WybierzZestawDoNaukiAdapter(ArrayList<Zestaw> zestawy, RecyclerView recyclerView) {
        this.zestawy = zestawy;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zestaw_wybor_zestawu_do_nauki, viewGroup, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Context context = v.getContext();
                Intent intent = new Intent(context, NaukaZestawuActivity.class);
                intent.putExtra("idZestawu", zestawy.get(position).getId());
                context.startActivity(intent);

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
}
