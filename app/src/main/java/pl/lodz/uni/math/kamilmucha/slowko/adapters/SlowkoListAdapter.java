package pl.lodz.uni.math.kamilmucha.slowko.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import pl.lodz.uni.math.kamilmucha.slowko.R;
import pl.lodz.uni.math.kamilmucha.slowko.database.model.Slowko;


public class SlowkoListAdapter extends ArrayAdapter<Slowko> {

    private Context mContext;
    private int mResource;


    public SlowkoListAdapter(Context context, int resource, ArrayList<Slowko> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        int id = getItem(position).get_id();
        String slowko = getItem(position).getSlowko();
        String tlumaczenie = getItem(position).getTlumaczenie();
        final boolean czyUmie = getItem(position).isCzyUmie();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView slowkoTextView = convertView.findViewById(R.id.slowko_layout_slowko);
        TextView tlumaczenieTextView = convertView.findViewById(R.id.slowko_layout_tlumaczenie);
       // final CheckBox czyUmieTextBox = convertView.findViewById(R.id.slowko_layout_checkbox);

        slowkoTextView.setText(slowko);
        tlumaczenieTextView.setText(tlumaczenie);
     //   czyUmieTextBox.setChecked(czyUmie);


        slowkoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext, "wiersz przycisniety", Toast.LENGTH_SHORT).show();
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });
        tlumaczenieTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext, "wiersz przycisniety", Toast.LENGTH_SHORT).show();
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });


     //   czyUmieTextBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      //      @Override
     //       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
     //           Toast.makeText(mContext, "CheckBox Clicked", Toast.LENGTH_SHORT).show();
    //        }
    //    });

        return convertView;

    }

}

