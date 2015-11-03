package com.dragon.calendarprovidertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetail extends AppCompatActivity {

    TextView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        label = (TextView) findViewById(R.id.lbl_event_title);
        label.setText(getIntent().getExtras().getString("label"));
    }
}
