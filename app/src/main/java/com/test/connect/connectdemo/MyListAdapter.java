package com.test.connect.connectdemo;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by pranjul on 27/10/17.
 */

public class MyListAdapter extends ArrayAdapter<Contact> {

    ArrayList<Contact> objects;
    int resource;
    String tag;
    Context context;

    public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final Holder holder;
        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view=inflater.inflate(resource,viewGroup,false);
            holder = new Holder();
            holder.textView = view.findViewById(R.id.text_list_row);
            view.setTag(holder);
        } else {
            holder = (Holder)view.getTag();
        }

        Contact contact = objects.get(i);

        holder.textView.setText(contact.getName());

        return view;
    }

    static class Holder{
        TextView textView;
    }
}
