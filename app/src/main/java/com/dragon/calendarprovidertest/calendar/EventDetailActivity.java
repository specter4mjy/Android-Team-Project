package com.dragon.calendarprovidertest.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dragon.calendarprovidertest.R;

public class EventDetailActivity extends AppCompatActivity {

    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = (TextView) findViewById(R.id.lbl_event_title);
        title.setText(getIntent().getExtras().getString("title"));
    }
}
