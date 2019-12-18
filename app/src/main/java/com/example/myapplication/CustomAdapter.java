package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.example.myapplication.models.Event;
import com.example.myapplication.ui.create_events.event_fragment;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Event> list = new ArrayList<Event>();
    private Context context;
    private Event event;

    public CustomAdapter(ArrayList<Event> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    public String getKey(int pos) {
        return list.get(pos).getKey();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);

        }

        //Handle TextView and display string from your list
        TextView tvContact= (TextView) view.findViewById(R.id.list_item_event_name);
        tvContact.setText(list.get(position).getNameOfEvent());

        //Handle buttons and add onClickListeners
        Button callbtn= (Button)view.findViewById(R.id.list_item_event_more_infO);

        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(context, event_fragment.class);
                intent.putExtra("event", event);
                context.startActivity(intent);

//                startActivity(intent);
                notifyDataSetChanged();
            }
        });
//        addBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//
//            }
//        });

        return view;
    }
}