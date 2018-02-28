package com.example.san.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.san.myapplication.Module.Event;
import com.example.san.myapplication.R;

import java.util.List;

/**
 * Created by vaps on 20-Feb-18.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>
{

    private List<Event> eventList;

    public EventsAdapter(List<Event> eventList)
    {
        this.eventList = eventList;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEventname,txtEventStartdate,txtEventEndDate;

        public EventsViewHolder(View itemView) {
            super(itemView);
            txtEventname = (TextView) itemView.findViewById(R.id.event_name);
            txtEventStartdate = (TextView) itemView.findViewById(R.id.eventStartdate);
            txtEventEndDate = (TextView) itemView.findViewById(R.id.eventEnddate);
        }
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);

        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {

        Event event = eventList.get(position);
        holder.txtEventname.setText(event.Event_name);
        holder.txtEventStartdate.setText(event.Start_date);
        holder.txtEventEndDate.setText(event.End_date);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
