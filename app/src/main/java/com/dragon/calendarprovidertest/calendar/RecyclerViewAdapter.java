package com.dragon.calendarprovidertest.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragon.calendarprovidertest.R;

import java.util.List;

/**
 * Created by specter on 10/24/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {
    private List<EventDataModel> events;
    private SparseBooleanArray selectedItems;
    private Context context;
    private Activity activity;

    public RecyclerViewAdapter(Activity activity, List<EventDataModel> events) {
        this.events = events;
        this.activity = activity;
    }

    public void addEvent(EventDataModel newModelData, int position) {
        events.add(position, newModelData);
        notifyItemInserted(position);
    }


    public void removeEvent(int position) {
        events.remove(position);
        notifyItemRemoved(position);
    }

    public EventDataModel getEvent(int position) {
        return events.get(position);
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        EventDataModel model = events.get(position);
        holder.label.setText(model.label);
        holder.startTime.setText(model.startTime);
        holder.endTime.setText(model.endTime);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }



    public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView label;
        TextView startTime;
        TextView endTime;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            startTime= (TextView) itemView.findViewById(R.id.txt_start_time);
            endTime= (TextView) itemView.findViewById(R.id.txt_end_time);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            EventDataModel event =events.get(position);
//            Snackbar.make(v, "clicked " +event.label, Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(activity, EventDetailActivity.class);
            intent.putExtra("label", event.label);
            intent.putExtra("startTime", event.startTime);
            intent.putExtra("endTime", event.endTime);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, (View) label, "event_title");
            activity.startActivity(intent, options.toBundle());


        }
    }
}