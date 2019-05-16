package pl.lodz.uni.math.kamilmucha.slowko;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class DodajZestawDialog extends AppCompatDialogFragment {
    private EditText editTextZestaw;

    private DodajZestawDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dodaj_zestaw, null);

        builder.setView(view)
                .setTitle("Dodaj zestaw")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nazwaZestawu = editTextZestaw.getText().toString();
                        listener.przeslijZestaw(nazwaZestawu);
                    }
                });

        editTextZestaw = view.findViewById(R.id.editTextDialogDodajZestaw);
        editTextZestaw.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextZestaw.getText().toString().equals("")) {
                    okButtonEnabled(true);
                } else {
                    okButtonEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DodajZestawDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Zaimplement DodajDialogListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        okButtonEnabled(false);
    }

    private void okButtonEnabled(boolean condition) {
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(condition);
    }

    public interface DodajZestawDialogListener {
        void przeslijZestaw(String nazwa);
    }
}
