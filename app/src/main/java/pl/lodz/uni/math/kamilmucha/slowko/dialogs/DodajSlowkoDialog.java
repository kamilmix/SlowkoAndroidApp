package pl.lodz.uni.math.kamilmucha.slowko.dialogs;

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

import pl.lodz.uni.math.kamilmucha.slowko.R;


public class DodajSlowkoDialog extends AppCompatDialogFragment {
    private EditText editTextSlowko;
    private EditText editTextTlumaczenie;
    private DodajDialogListener listener;

    private String slowko;
    private String tlumaczenie;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_dodaj_slowko, null);

        editTextSlowko = view.findViewById(R.id.dialogEditTextSlowko);
        editTextTlumaczenie = view.findViewById(R.id.dialogEditTextTlumaczenie);

        slowko = editTextSlowko.getText().toString();
        tlumaczenie = editTextTlumaczenie.getText().toString();

        editTextSlowko.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                slowko = editTextSlowko.getText().toString();
                tlumaczenie = editTextTlumaczenie.getText().toString();
                okButtonEnabled(isTextInEditTexts());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextTlumaczenie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                slowko = editTextSlowko.getText().toString();
                tlumaczenie = editTextTlumaczenie.getText().toString();
                okButtonEnabled(isTextInEditTexts());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                        listener.przeslijSlowko(slowko, tlumaczenie);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DodajDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Zaimplement DodajDialogListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        okButtonEnabled(false);
    }

    private boolean isTextInEditTexts() {
        return !slowko.equals("") && !tlumaczenie.equals("");
    }

    private void okButtonEnabled(boolean condition) {
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(condition);
    }

    public interface DodajDialogListener {
        void przeslijSlowko(String slowko, String tlumaczenie);
    }
}
