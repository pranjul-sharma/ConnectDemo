package com.test.connect.connectdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.View.GONE;


public class CreateGroupFragment extends Fragment {

    ListView listView;
    EditText editTextGroupName;
    long grpIdx;
    TextInputLayout textInputLayout;
    Button btnContinue,btnCreateGroup;
    public CreateGroupFragment(){
        //empty constructor required
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_create_group,container,false);
        listView = view.findViewById(R.id.list_group_create);
        editTextGroupName= view.findViewById(R.id.edit_group_name);
        btnContinue = view.findViewById(R.id.btn_continue);
        btnCreateGroup = view.findViewById(R.id.btn_create_group);
        textInputLayout = view.findViewById(R.id.text_layout_name);
        final ContactDBHelper dbHelper=new ContactDBHelper(getContext());
        ArrayList<Contact> contacts = dbHelper.readContacts();
        final CheckableListAdapter adapter = new CheckableListAdapter(getContext(),R.layout.list_group_item_select_row,contacts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.toggleChecked(i);
            }
        });
        listView.setVisibility(GONE);
        btnCreateGroup.setVisibility(GONE);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grpIdx = dbHelper.createGroup(editTextGroupName.getText().toString());
                if (grpIdx == -1){
                    Snackbar.make(view,"Group already exist. Please use another name.",Snackbar.LENGTH_SHORT).show();
                } else {
                    adapter.uncheckAll();
                    adapter.notifyDataSetChanged();
                    btnContinue.setVisibility(GONE);
                    editTextGroupName.setVisibility(GONE);
                    textInputLayout.setVisibility(GONE);
                    listView.setVisibility(View.VISIBLE);
                    btnCreateGroup.setVisibility(View.VISIBLE);

                }
            }
        });

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Contact> resultList = adapter.getCheckedItems();
                ArrayList<Integer> contactsId = new ArrayList<>();
                for(int i=0;i<resultList.size();i++){
                    contactsId.add(resultList.get(i).getId());
                }

                dbHelper.insertGroupDetails(grpIdx,contactsId);

                listView.setVisibility(GONE);
                btnContinue.setVisibility(View.VISIBLE);
                textInputLayout.setVisibility(View.VISIBLE);
                editTextGroupName.setVisibility(View.VISIBLE);
                btnCreateGroup.setVisibility(GONE);
                Toast.makeText(getContext(),"group created",Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
}
