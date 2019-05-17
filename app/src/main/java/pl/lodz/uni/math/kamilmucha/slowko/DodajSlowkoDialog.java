package pl.lodz.uni.math.kamilmucha.slowko;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;


public class DodajSlowkoDialog extends AppCompatDialogFragment {
    private EditText editTextSlowko;
    private EditText editTextTlumaczenie;
    private DodajDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dodaj_slowko, null);

        builder.setView(view)
                .setTitle("Dodaj słówko")
                .setNegativeButton("anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String slowko = editTextSlowko.getText().toString();
                        String tlumaczenie = editTextTlumaczenie.getText().toString();
                        listener.przeslijSlowko(slowko, tlumaczenie);
                    }
                });
        editTextSlowko = view.findViewById(R.id.dialogEditTextSlowko);
        editTextTlumaczenie = view.findViewById(R.id.dialogEditTextTlumaczenie);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        DatabaseManager.initializeInstance(databaseHelper);

        try {
            listener = (DodajDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Zaimplement DodajDialogListener");
        }
    }

    public interface DodajDialogListener {
        void przeslijSlowko(String slowko, String tlumaczenie);
    }
}
