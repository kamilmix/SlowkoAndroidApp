package pl.lodz.uni.math.kamilmucha.slowko;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.ZestawDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

class ZestawyAdapter extends RecyclerView.Adapter {
    public static final String EXTRA_MESSAGE = "pl.lodz.uni.math.kamilmucha.slowko.ZESTAW";
    private ArrayList<Zestaw> zestawy;
    private RecyclerView recyclerView;

    private static DatabaseHelper databaseHelper;

    private ZestawDAO zestawDAO;

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
       databaseHelper = new DatabaseHelper(view.getContext());
       zestawDAO = new ZestawDAO();

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int position = recyclerView.getChildAdapterPosition(v);
               Context context = v.getContext();
               Intent intent = new Intent(context, ZestawyActivity.class);
               intent.putExtra("idZestawu", zestawy.get(position).getId());
               intent.putExtra("nazwaZestawu", zestawy.get(position).getNazwa());
               context.startActivity(intent);
           }
       });

       view.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(final View v) {
               new AlertDialog.Builder(v.getContext())
                       .setTitle("Delete entry")
                       .setMessage("Are you sure you want to delete this entry?")

                       // Specifying a listener allows you to take an action before dismissing the dialog.
                       // The dialog is automatically dismissed when a dialog button is clicked.
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               int position = recyclerView.getChildAdapterPosition(v);
                               zestawDAO.delete(zestawy.get(position).getId());
                               zestawy.remove(position);
                               notifyItemRemoved(position);
                           }
                       })

                       // A null listener allows the button to dismiss the dialog and take no further action.
                       .setNegativeButton(android.R.string.no, null)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .show();
               return true;
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
