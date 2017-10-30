package com.test.connect.connectdemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pranjul on 27/10/17.
 */

public class CheckableListAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact> objects;
    Context context;
    int resource;
    private HashMap<Integer,Boolean> myChecked = new HashMap<>();

    public CheckableListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        uncheckAll();
    }

    public void toggleChecked(int position){
        if(myChecked.get(position)){
            myChecked.put(position, false);
        }else{
            myChecked.put(position, true);
        }

        notifyDataSetChanged();
    }

    public ArrayList<Integer> getCheckedItemPositions(){
        ArrayList<Integer> checkedItemPositions = new ArrayList<Integer>();

        for(int i = 0; i < myChecked.size(); i++){
            if (myChecked.get(i)){
                (checkedItemPositions).add(i);
            }
        }

        return checkedItemPositions;
    }
    public void uncheckAll(){
        for(int i = 0 ; i<objects.size(); i++ ){
            myChecked.put(i,false);
        }
    }
    public ArrayList<Contact> getCheckedItems(){
        ArrayList<Contact> checkedItems = new ArrayList<>();

        for(int i = 0; i < myChecked.size(); i++){
            if (myChecked.get(i)){
                (checkedItems).add(objects.get(i));
            }
        }

        return checkedItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(resource, parent, false);
        }

        CheckedTextView checkedTextView = row.findViewById(R.id.text_list_row_checked);
        checkedTextView.setText(objects.get(position).getName());

        Boolean checked = myChecked.get(position);
        if (checked != null) {
            checkedTextView.setChecked(checked);
        }

        return row;
    }


}
