package com.example.muhammadumar.sawlection.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muhammadumar.sawlection.R;
import com.example.muhammadumar.sawlection.data.DataRangking;

import java.util.List;

public class AdapterRangking extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataRangking> items;

    public AdapterRangking(Activity activity, List<DataRangking> items) {
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_rangking, null);
        TextView id_rangking = (TextView) convertView.findViewById(R.id.id_rangking);
        TextView id_pendaftar = (TextView) convertView.findViewById(R.id.id_pendaftar);
        TextView id_pendaftar_pil = (TextView) convertView.findViewById(R.id.id_pendaftar_pil);
        TextView id_kriteria = (TextView) convertView.findViewById(R.id.id_kriteria);
        TextView id_kriteria_pil = (TextView) convertView.findViewById(R.id.id_kriteria_pil);
        TextView nilai = (TextView) convertView.findViewById(R.id.nilai);


        DataRangking data = items.get(position);

        id_rangking.setText(data.getId_rangking());
        id_pendaftar.setText(data.getId_pendaftar());
        id_pendaftar_pil.setText(data.getId_pendaftar_pil());
        id_kriteria.setText(data.getId_kriteria());
        id_kriteria_pil.setText(data.getId_kriteria_pil());
        nilai.setText(data.getNilai());

        return convertView;
    }
}
