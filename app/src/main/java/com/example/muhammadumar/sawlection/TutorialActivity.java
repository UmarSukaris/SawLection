package com.example.muhammadumar.sawlection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class TutorialActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.tvlihat);
        textView.setText(Html.fromHtml(getString(R.string.htmlFormattedText)));
    }
}
