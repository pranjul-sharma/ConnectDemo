package com.test.connect.connectdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ContactListFragment extends Fragment {

    ListView listView;
    public ContactListFragment(){
        //required empty constructor
    }
    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_home,container,false);
        listView=rootView.findViewById(R.id.listview);
        final TextView textView = rootView.findViewById(R.id.placeholder_text);
        ContactDBHelper dbHelper = new ContactDBHelper(getContext());
        final ArrayList<Contact> list = dbHelper.readContacts();
        Collections.sort(list);
        if (list.size()==0) {
            textView.setText("No Contact is saved yet");
            listView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.GONE);
            MyListAdapter listAdapter = new MyListAdapter(getContext(),R.layout.list_contact_row, list);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.custom_dialog_view);
                    dialog.setCancelable(false);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT );
                    Button btnDismiss = dialog.findViewById(R.id.btn_dismiss_dialog);
                    TextView ttName= dialog.findViewById(R.id.tt_dialog_name);
                    TextView ttEmail= dialog.findViewById(R.id.tt_dialog_email);
                    TextView ttPhone= dialog.findViewById(R.id.tt_dialog_phone);

                    ttName.setText(list.get(i).getName());
                    ttPhone.setText(String.valueOf(list.get(i).getPhone()));
                    ttEmail.setText(list.get(i).getEmail());
                    btnDismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
