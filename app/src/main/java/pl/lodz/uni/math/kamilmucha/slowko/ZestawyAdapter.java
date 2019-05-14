package pl.lodz.uni.math.kamilmucha.slowko;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.ZestawDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Zestaw;

class ZestawyAdapter extends RecyclerView.Adapter {
    public static final String EXTRA_MESSAGE = "pl.lodz.uni.math.kamilmucha.slowko.ZESTAW";
    private ArrayList<Zestaw> zestawy;
    private RecyclerView recyclerView;

    private ZestawDAO zestawDAO;
    private SlowkoDAO slowkoDAO;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mNazwaZestawu;
        public TextView liczbaSlowek;
        public ProgressBar progressBar;


        public MyViewHolder(View pItem) {
            super(pItem);
            mNazwaZestawu = pItem.findViewById(R.id.nazwa_zestawu);
            liczbaSlowek = pItem.findViewById(R.id.textViewLiczbaSlowekWZestawie);
            progressBar = pItem.findViewById(R.id.progressBarIleDoNauki);
        }
    }

    public ZestawyAdapter(ArrayList<Zestaw> zestawy, RecyclerView recyclerView) {
        this.zestawy = zestawy;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zestaw_layout, viewGroup, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
        DatabaseManager.initializeInstance(databaseHelper);

        zestawDAO = new ZestawDAO();
        slowkoDAO = new SlowkoDAO();

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
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), v, Gravity.END);
                //inflating menu from xml resource
                popup.inflate(R.menu.zestaw_options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                showRenameDialog();
                                return true;
                            case R.id.menu2:
                                showDeleteDialog();
                                return true;
                            default:
                                return false;
                        }
                    }

                    private void showRenameDialog() {
                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                v.getContext());
                        alert.setTitle("Zmień nazwę");

                        final EditText input = new EditText(v.getContext());
                        final int position = recyclerView.getChildAdapterPosition(v);
                        input.setText(zestawy.get(position).getNazwa());
                        alert.setView(input);

                        alert.setPositiveButton("Zmień", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String newName = input.getEditableText().toString();
                                zestawDAO.rename(zestawy.get(position).getId(), newName);
                                zestawy.get(position).setNazwa(newName);
                                notifyItemChanged(position);
                            }
                        });

                        alert.setNegativeButton("Anuluj",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    }

                    private void showDeleteDialog() {
                        new AlertDialog.Builder(v.getContext())
                                .setTitle("Usuń zestaw")
                                .setMessage("Tej operacji nie można cofnąć!")
                                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        int position = recyclerView.getChildAdapterPosition(v);
                                        zestawDAO.delete(zestawy.get(position).getId());
                                        zestawy.remove(position);
                                        notifyItemRemoved(position);
                                    }
                                })
                                .setNegativeButton("Anuluj", null)
                                .setIcon(R.drawable.ic_warning_black_24dp)
                                .show();
                    }
                });
                popup.show();
                return true;
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Zestaw zestaw = zestawy.get(i);
        ((MyViewHolder) viewHolder).mNazwaZestawu.setText(zestaw.getNazwa());
        int count = slowkoDAO.getCount(zestaw.getId());
        int countDoNauki = slowkoDAO.getCountPozostaleDoNauki(zestaw.getId());

        if (count != 0) {
            ((MyViewHolder) viewHolder).liczbaSlowek.setText(countDoNauki + "/" + count);

            ((MyViewHolder) viewHolder).progressBar.setMax(count);
            ((MyViewHolder) viewHolder).progressBar.setProgress(countDoNauki);
            ((MyViewHolder) viewHolder).progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return zestawy.size();
    }
}
