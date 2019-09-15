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
import com.example.muhammadumar.sawlection.data.DataPendaftar;

import java.util.List;

public class AdapterPendaftar extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPendaftar> items;

    public AdapterPendaftar(Activity activity, List<DataPendaftar> items) {
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
            convertView = inflater.inflate(R.layout.list_row_pendaftar, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nim = (TextView) convertView.findViewById(R.id.nim);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);


        DataPendaftar data = items.get(position);

        id.setText(data.getId());
        nim.setText(data.getNim());
        nama.setText(data.getNama_pendaftar());
        return convertView;
    }

}

