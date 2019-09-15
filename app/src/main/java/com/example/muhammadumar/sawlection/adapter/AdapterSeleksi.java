package com.example.muhammadumar.sawlection.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muhammadumar.sawlection.R;
import com.example.muhammadumar.sawlection.data.DataSeleksi;

import java.util.List;

public class AdapterSeleksi extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSeleksi> items;

    public AdapterSeleksi(Activity activity, List<DataSeleksi> items) {
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
            convertView = inflater.inflate(R.layout.list_row_seleksi, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama_seleksi = (TextView) convertView.findViewById(R.id.nama_seleksi);
        TextView id_laboratorium = (TextView) convertView.findViewById(R.id.id_laboratorium);
        TextView id_laboratorium_pil = (TextView) convertView.findViewById(R.id.id_laboratorium_pil);
        TextView tahun_ajaran = (TextView) convertView.findViewById(R.id.tahun_ajaran);
        TextView kuota = (TextView) convertView.findViewById(R.id.kuota);
        TextView deskripsi = (TextView) convertView.findViewById(R.id.deskripsi);

        DataSeleksi data = items.get(position);

        id.setText(data.getId());
        nama_seleksi.setText(data.getNama_seleksi());
        id_laboratorium.setText(data.getId_laboratorium());
        id_laboratorium_pil.setText(data.getId_laboratorium_pil());
        tahun_ajaran.setText(data.getTahun_ajaran());
        kuota.setText(data.getKuota());
        deskripsi.setText(data.getDeskripsi());

        return convertView;
    }

}
