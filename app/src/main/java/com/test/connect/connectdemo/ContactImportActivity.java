package com.test.connect.connectdemo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactImportActivity extends AppCompatActivity {

    ListView listView;
    Button button,buttonImportAll;
    ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_import);
        listView = (ListView)findViewById(R.id.list_select);
        button = (Button) findViewById(R.id.btn_select);
        buttonImportAll = (Button) findViewById(R.id.btn_select_all);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Select contact to import");
        setSupportActionBar(toolbar);
        contentResolver = getContentResolver();
        String[] projection = new String[]
                {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
                };



        final Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC" );
        ArrayList<Contact> contacts = new ArrayList<>();
        if(cursor!=null && cursor.moveToFirst()) {

            int colNameIdx = cursor.getColumnIndex(projection[0]);
            int colContactIdIdx = cursor.getColumnIndex(projection[1]);
            do{
                String name = cursor.getString(colNameIdx);
                int id = Integer.parseInt(cursor.getString(colContactIdIdx));
                Contact contact = new Contact();
                contact.setName(name);
                contact.setId(id);
                contacts.add(contact);

            }while (cursor.moveToNext()) ;
            cursor.close();
        }

        final CheckableListAdapter adapter = new CheckableListAdapter(ContactImportActivity.this,R.layout.list_group_item_select_row,contacts);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Contact> items = adapter.getCheckedItems();
                if (items.size() == 0){
                    Toast.makeText(getApplicationContext(),"please select at least one contact.",Toast.LENGTH_LONG).show();
                }else {
                    Snackbar.make(view, "contacts will be imported shortly.", Snackbar.LENGTH_SHORT).show();
                    ContactDBHelper dbHelper = new ContactDBHelper(getApplicationContext());
                    String[] projectionNum = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    String[] projectionEmail = new String[]{ContactsContract.CommonDataKinds.Email.DATA};

                    for (Contact contact : items) {
                        String phone = null, email = null;
                        Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionNum, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "= ?" ,new String[]{contact.getName()} , null, null);
                        if (cursor1 != null && cursor1.moveToFirst()) {
                            phone = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            cursor1.close();
                        }
                        Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projectionEmail, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contact.getId(), null, null);
                        if (cursor2 != null && cursor2.moveToFirst()) {
                            email = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            cursor2.close();
                        }
                        contact.setPhone(phone);
                        contact.setEmail(email);
                        Long returnId = dbHelper.insertRecord(contact);
                        if (returnId == -1)
                            Toast.makeText(getApplicationContext(), contact.getPhone() + " is already saved with another name.", Toast.LENGTH_SHORT).show();
                    }
                    Snackbar.make(view, "contact import finished", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.toggleChecked(i);
            }
        });

        buttonImportAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImportAll(){
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        Toast.makeText(getApplicationContext(),"All contacts will be imported soon.",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(getApplicationContext(),"All contacts imported.",Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                finish();
            }
        });
    }

    private class ImportAll extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            ContactDBHelper dbHelper = new ContactDBHelper(getApplicationContext());
            String[] projectionNum =new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.CONTACT_ID};
            String[] projectionEmail = new String[] {ContactsContract.CommonDataKinds.Email.DATA};
            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projectionNum,null,null,null);
            if(cursor!=null){
                int nameIdx = cursor.getColumnIndex(projectionNum[0]);
                int phoneIdx = cursor.getColumnIndex(projectionNum[1]);
                int emailIdIdx = cursor.getColumnIndex(projectionNum[2]);
                cursor.moveToFirst();
                do{
                    String name = cursor.getString(nameIdx);
                    String phone = cursor.getString(phoneIdx).replaceAll("\\s+","");
                    int emailid = Integer.parseInt(cursor.getString(emailIdIdx));
                    Cursor cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,projectionEmail, ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+emailid,null,null);
                    String email=null;
                    if(cursor1!=null && cursor1.moveToFirst()){
                        email = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        cursor1.close();
                    }
                    Contact contact = new Contact(name,email,phone);
                    dbHelper.insertRecord(contact);

                }while (cursor.moveToNext());
                cursor.close();
            }
            return null;
        }
    }
}
