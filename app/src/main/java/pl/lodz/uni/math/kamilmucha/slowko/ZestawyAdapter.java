package pl.lodz.uni.math.kamilmucha.slowko;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

class ZestawyAdapter extends RecyclerView.Adapter {
    private ArrayList<Zestaw> zestawy;
    private RecyclerView recyclerView;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mNazwaZestawu;


        public MyViewHolder(View pItem) {
            super(pItem);
            mNazwaZestawu = (TextView) pItem.findViewById(R.id.nazwa_zestawu);

        }
    }


    public ZestawyAdapter(ArrayList<Zestaw> zestawy, RecyclerView recyclerView) {
        this.zestawy = zestawy;
        this.recyclerView = recyclerView;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zestaw_layout, viewGroup, false);

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position = recyclerView.getChildAdapterPosition(v);
               Context context = v.getContext();
               Intent intent = new Intent(context, ZestawyActivity.class);
               intent.putExtra("idZestawu", zestawy.get(position).getId());
               context.startActivity(intent);
           }
       });

       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Zestaw zestaw = zestawy.get(i);
        ((MyViewHolder) viewHolder).mNazwaZestawu.setText(zestaw.getNazwa());

    }

    @Override
    public int getItemCount() {
        return zestawy.size();
    }
}
