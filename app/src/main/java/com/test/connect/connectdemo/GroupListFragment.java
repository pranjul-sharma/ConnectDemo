package com.test.connect.connectdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by pranjul on 27/10/17.
 */

public class GroupListFragment extends Fragment {
    ListView listView;
    public GroupListFragment(){

    }

    public static GroupListFragment newInstance(){
        return  new GroupListFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_home, container, false);
        listView = rootView.findViewById(R.id.listview);

        TextView textView = rootView.findViewById(R.id.placeholder_text);
        final ContactDBHelper dbHelper = new ContactDBHelper(getContext());
        final ArrayList<Group> list = dbHelper.readGroups();
        if (list.size() == 0) {
            textView.setText("No Group is created yet");
            listView.setVisibility(GONE);
        } else {
            textView.setVisibility(GONE);
            MyGroupAdapter listAdapter = new MyGroupAdapter(getContext(), R.layout.list_contact_row, list);
            listView.setAdapter(listAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_dialog_view2);
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT );
                TextView ttGrpName = dialog.findViewById(R.id.tt_dialog_group_name);
                Group group = list.get(i);
                ttGrpName.setText(group.getGrpName());
                ListView listView = dialog.findViewById(R.id.list_grp_details);
                TextView textView1 = dialog.findViewById(R.id.place);

                group = dbHelper.readGroupInfo(group.getGrpId());
                if(group.getMembers().size()!=0){
                    textView1.setVisibility(GONE);
                }
                MyListAdapter adapter = new MyListAdapter(getContext(),R.layout.list_contact_row,group.getMembers());
                listView.setAdapter(adapter);
                Button button = dialog.findViewById(R.id.btn_dismiss_dialog);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return rootView;
    }
}
