package com.example.muhammadumar.sawlection.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muhammadumar.sawlection.R;
import com.example.muhammadumar.sawlection.data.DataKriteria;

import java.util.List;

public class AdapterKriteria extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataKriteria> items;

    public AdapterKriteria(Activity activity, List<DataKriteria> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_kriteria, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView kriteria = (TextView) convertView.findViewById(R.id.kriteria);
        TextView tipe = (TextView) convertView.findViewById(R.id.tipe);
        TextView bobot = (TextView) convertView.findViewById(R.id.bobot);

        DataKriteria data = items.get(position);

        id.setText(data.getId());
        kriteria.setText(data.getKriteria());
        tipe.setText(data.getTipe());
        bobot.setText(data.getBobot());

        return convertView;
    }

}
