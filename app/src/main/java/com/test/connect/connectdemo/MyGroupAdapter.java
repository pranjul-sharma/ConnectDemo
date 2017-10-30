package com.test.connect.connectdemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pranjul on 28/10/17.
 */

public class MyGroupAdapter extends ArrayAdapter<Group> {

    private Context context;
    private ArrayList<Group> groups;
    int resource;
    public MyGroupAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Group> objects) {
        super(context, resource, objects);
        this.context = context;
        this.groups = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if(view == null ){
            holder = new Holder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(resource,parent,false);
            holder.textView = view.findViewById(R.id.text_list_row);
            view.setTag(holder);
        }else {
            holder = (Holder)view.getTag();
        }

        holder.textView.setText(groups.get(position).getGrpName());

        return view;
    }

    static class Holder{
        TextView textView;
    }
}
