package pl.lodz.uni.math.kamilmucha.slowko.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.R;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;
import pl.lodz.uni.math.kamilmucha.slowko.dialogs.EdytujSlowkoDialog;

public class SlowkaWZestawieAdapter extends RecyclerView.Adapter {
    private ArrayList<Slowko> slowka;
    private RecyclerView recyclerView;
    private AppCompatActivity appCompatActivity;

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

    public SlowkaWZestawieAdapter(ArrayList<Slowko> slowka, RecyclerView recyclerView, AppCompatActivity appCompatActivity) {
        this.slowka = slowka;
        this.recyclerView = recyclerView;
        this.appCompatActivity = appCompatActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slowko_layout, viewGroup, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
        DatabaseManager.initializeInstance(databaseHelper);

        slowkoDAO = new SlowkoDAO();

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onLongClickSlowko(v);
            }
        });

        return new MyViewHolder(view);
    }

    private boolean onLongClickSlowko(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.END);
        popupMenu.inflate(R.menu.slowko_options_menu);
        final View view = v;
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onSlowkoMenuItemClick(item, view);
            }
        });
        popupMenu.show();

        return true;
    }

    private boolean onSlowkoMenuItemClick(MenuItem item, View view) {
        switch (item.getItemId()) {
            case R.id.MenuSlowkoEdytuj:
                showEditDialog(view);
                return true;
            case R.id.MenuSlowkoUsun:
                showDeleteDialog(view);
                return true;
            default:
                return false;
        }
    }

    private void showEditDialog(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Slowko slowko = slowka.get(position);
        EdytujSlowkoDialog edytujSlowkoDialog = new EdytujSlowkoDialog();

        Bundle args = new Bundle();
        args.putInt("id", slowko.get_id());
        edytujSlowkoDialog.setArguments(args);
        edytujSlowkoDialog.show(appCompatActivity.getSupportFragmentManager(), "edit_dialog");
    }

    private void showDeleteDialog(final View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle("Usuń słówko")
                .setMessage("Tej operacji nie można cofnąć!")
                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int position = recyclerView.getChildAdapterPosition(v);
                        slowkoDAO.deleteSlowkoById(slowka.get(position).get_id());
                        slowka.remove(position);
                        notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Anuluj", null)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Slowko slowko = slowka.get(i);
        ((MyViewHolder) viewHolder).textViewSlowko.setText(slowko.getSlowko());
        ((MyViewHolder) viewHolder).textViewTlumaczenie.setText(slowko.getTlumaczenie());
        if (slowko.isCzyUmie()) {
            ((MyViewHolder) viewHolder).checkCzyUmie.setVisibility(View.VISIBLE);
        }
        else {
            ((MyViewHolder) viewHolder).checkCzyUmie.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return slowka.size();
    }
}
