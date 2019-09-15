package com.example.muhammadumar.sawlection.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.muhammadumar.sawlection.R;
import com.example.muhammadumar.sawlection.data.DataPanitia;
import com.example.muhammadumar.sawlection.data.DataRangking;

import java.util.List;

public class AdapterPanitia extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPanitia> items;

    public AdapterPanitia(Activity activity, List<DataPanitia> items) {
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
            convertView = inflater.inflate(R.layout.list_row_panitia, null);
        TextView id_panitia = (TextView) convertView.findViewById(R.id.id_panitia);
        TextView id_user = (TextView) convertView.findViewById(R.id.id_user);
        TextView id_user_pil = (TextView) convertView.findViewById(R.id.id_user_pil);
        TextView id_seleksi = (TextView) convertView.findViewById(R.id.id_seleksi);

        DataPanitia data = items.get(position);

        id_panitia.setText(data.getId_panitia());
        id_user.setText(data.getId_user());
        id_user_pil.setText(data.getId_user_pil());
        id_seleksi.setText(data.getId_seleksi());

        return convertView;
    }
}
