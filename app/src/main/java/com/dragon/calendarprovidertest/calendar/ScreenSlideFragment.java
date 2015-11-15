package com.dragon.calendarprovidertest.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon.calendarprovidertest.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by specter on 10/24/15.
 */
public class ScreenSlideFragment extends android.support.v4.app.Fragment{
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    int itemCount;
    Context mContext;


    private static final String DEBUG_TAG = "MyActivity";

    public static final String[] INSTANCE_PROJECTION = new String[]{
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.OWNER_ACCOUNT,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.END_DAY,
            CalendarContract.Instances.DESCRIPTION,
            CalendarContract.Instances.ALL_DAY,
            CalendarContract.Instances.END
    };

    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_BEGIN_INDEX = 1;
    private static final int PROJECTION_TITLE_INDEX = 2;
    private static final int PROJECTION_OWNER_INDEX = 3;
    private static final int PROJECTION_SART_DAY_INDEX = 4;
    private static final int PROJECTION_END_DAY_INDEX = 5;
    private static final int PROJECTION_DESC_INDEX = 6;
    private static final int PROJECTION_ALL_DAY_INDEX = 7;
    private static final int PROJECTION_END_INDEX = 8;
    Calendar nowTime;
    private int page;
    public static ScreenSlideFragment newInstance(int page) {
        ScreenSlideFragment screenSlideFragment = new ScreenSlideFragment();
        Bundle args = new Bundle();
        args.putInt("page",page);
        screenSlideFragment.setArguments(args);
        return screenSlideFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_main, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);


        DateFormat formatter = new SimpleDateFormat("HH/mm/ss/MM/dd/yyyy");

        page = getArguments().getInt("page");

        nowTime= Calendar.getInstance();
        nowTime.add(Calendar.DATE,page-1);
        Calendar beginTime = nowTime;
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        beginTime.set(Calendar.SECOND, 0);
        Log.d(DEBUG_TAG, beginTime.getTime().toString());
        Long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = nowTime;
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.SECOND,59);
        Log.d(DEBUG_TAG, endTime.getTime().toString());
        Long endMillis = endTime.getTimeInMillis();

        Cursor cursor = null;
        ContentResolver cr = getActivity().getContentResolver();


        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        cursor = cr.query(builder.build(),
                INSTANCE_PROJECTION,
                null,
                null, null);

        List<EventDataModel> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String title = null;
            long beginVal = 0;
            long endVal = 0;
            EventDataModel model = new EventDataModel();

            beginVal = cursor.getLong(PROJECTION_BEGIN_INDEX);
            endVal = cursor.getLong(PROJECTION_END_INDEX);
            title = cursor.getString(PROJECTION_TITLE_INDEX);


            Log.i(DEBUG_TAG, "Event: " + title);
            model.label=title;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((beginVal));
            Log.i(DEBUG_TAG, "Date: " + formatter.format(calendar.getTime()));
            model.startTime=formatter.format(calendar.getTime());
            calendar.setTimeInMillis(endVal);
            Log.i(DEBUG_TAG, "end Date: " + formatter.format(calendar.getTime()));
            model.endTime=formatter.format(calendar.getTime());

            items.add(model);
        }

        adapter = new RecyclerViewAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);

        TextView showDate = (TextView) rootView.findViewById(R.id.showDate);
        showDate.setText(nowTime.getTime().toString());
        return rootView;
    }
}
