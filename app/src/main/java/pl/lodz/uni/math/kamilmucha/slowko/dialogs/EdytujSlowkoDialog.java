package pl.lodz.uni.math.kamilmucha.slowko.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import pl.lodz.uni.math.kamilmucha.slowko.R;
import pl.lodz.uni.math.kamilmucha.slowko.database.DAO.SlowkoDAO;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseHelper;
import pl.lodz.uni.math.kamilmucha.slowko.database.DatabaseManager;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;


public class EdytujSlowkoDialog extends AppCompatDialogFragment {
    private EditText editTextSlowko;
    private EditText editTextTlumaczenie;
    private CheckBox checkBoxCzyUmiesz;

    private SlowkoDAO slowkoDAO;

    private EdytujSlowkoListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edytuj_slowko, null);

        DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
        DatabaseManager.initializeInstance(databaseHelper);

        slowkoDAO = new SlowkoDAO();

        editTextSlowko = view.findViewById(R.id.editTextDialogEditSlowkoSlowko);
        editTextTlumaczenie = view.findViewById(R.id.editTextDialogEditSlowkoTlumaczenie);
        checkBoxCzyUmiesz = view.findViewById(R.id.checkBoxDialogEdytujSlowkoCzyUmiesz);

        final Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        final Slowko slowko = slowkoDAO.getSlowkoById(id);

        editTextSlowko.setText(slowko.getSlowko());
        editTextTlumaczenie.setText(slowko.getTlumaczenie());
        checkBoxCzyUmiesz.setChecked(slowko.isCzyUmie());

        editTextTlumaczenie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                okButtonEnabled(isTextInEditTexts());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextSlowko.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                okButtonEnabled(isTextInEditTexts());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(view)
                .setTitle("Edytuj")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        slowko.setSlowko(editTextSlowko.getText().toString());
                        slowko.setTlumaczenie(editTextTlumaczenie.getText().toString());
                        slowko.setCzyUmie(checkBoxCzyUmiesz.isChecked());
                        slowkoDAO.update(slowko);
                        listener.przeslijEdytowaneSlowko(slowko);

                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (EdytujSlowkoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Zaimplement EdytujSlowkoListener");
        }
    }

    private boolean isTextInEditTexts() {
        return !editTextSlowko.getText().toString().equals("") && !editTextTlumaczenie.getText().toString().equals("");
    }

    private void okButtonEnabled(boolean condition) {
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(condition);
    }

    public interface EdytujSlowkoListener {
        void przeslijEdytowaneSlowko(Slowko slowko);
    }
}
