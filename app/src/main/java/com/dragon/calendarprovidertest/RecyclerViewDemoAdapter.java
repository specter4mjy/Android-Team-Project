package com.dragon.calendarprovidertest;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by specter on 10/24/15.
 */
public class RecyclerViewDemoAdapter extends RecyclerView.Adapter<RecyclerViewDemoAdapter.ListItemViewHolder> {
    private List<EventModel> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewDemoAdapter(List<EventModel> events) {
        items = events;
    }

    public void addEvent(EventModel newModelData, int position) {
        items.add(position, newModelData);
        notifyItemInserted(position);
    }


    public void removeEvent(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public EventModel getEvent(int position) {
        return items.get(position);
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        EventModel model = items.get(position);
        holder.label.setText(model.label);
        holder.startTime.setText(model.startTime);
        holder.endTime.setText(model.endTime);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView startTime;
        TextView endTime;


        public ListItemViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
            startTime= (TextView) itemView.findViewById(R.id.txt_start_time);
            endTime= (TextView) itemView.findViewById(R.id.txt_end_time);
        }
    }
}
