package com.test.connect.connectdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pranjul on 26/10/17.
 */

public class ContactDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts_groups.db";
    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_CONTACT = "CREATE TABLE "+ContactContract.ContactEntry.TABLE_NAME+" ( "+
                ContactContract.ContactEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContactContract.ContactEntry.COL_CONTACT_NAME +" TEXT NOT NULL, "+
                ContactContract.ContactEntry.COL_CONTACT_EMAIl +" TEXT, "+
                ContactContract.ContactEntry.COL_CONTACT_PHONE +" TEXT NOT NULL," +
                "CONSTRAINT phone_unique UNIQUE("+ ContactContract.ContactEntry.COL_CONTACT_PHONE+"))";

        final String CREATE_TABLE_GROUP = "CREATE TABLE "+ ContactContract.GroupEntry.TABLE_NAME +" ( "+
                ContactContract.GroupEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContactContract.GroupEntry.COL_GROUP_NAME+" TEXT NOT NULL," +
                "CONSTRAINT grp_unique UNIQUE("+ ContactContract.GroupEntry.COL_GROUP_NAME+"))";

        final String CREATE_TABLE_GROUP_DETAIL = "CREATE TABLE "+ ContactContract.GroupDetailEntry.TABLE_NAME +" ( "+
                ContactContract.GroupDetailEntry._ID + " INT NOT NULL, "+
                ContactContract.GroupDetailEntry.COL_GROUP_ID+" INT NOT NULL, " +
                "FOREIGN KEY ("+ ContactContract.GroupDetailEntry._ID+") REFERENCES "+ ContactContract.ContactEntry.TABLE_NAME +
                "("+ ContactContract.ContactEntry._ID+") , " +
                "FOREIGN KEY ("+ ContactContract.GroupDetailEntry.COL_GROUP_ID+") REFERENCES "+ ContactContract.GroupEntry.TABLE_NAME +
                "("+ ContactContract.GroupEntry._ID+") )";

        sqLiteDatabase.execSQL(CREATE_TABLE_CONTACT);
        Log.v("TABLE 1","table 1 created");
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP);
        Log.v("TABLE 2","table 2 created");
        sqLiteDatabase.execSQL(CREATE_TABLE_GROUP_DETAIL);
        Log.v("TABLE 3","table 3 created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        final String DROP_TABLE_CONTACT="DROP TABLE IF EXISTS "+ ContactContract.ContactEntry.TABLE_NAME;
        final String DROP_TABLE_GROUP="DROP TABLE IF EXISTS "+ ContactContract.GroupEntry.TABLE_NAME;
        final String DROP_TABLE_GROUP_DETAIL="DROP TABLE IF EXISTS "+ ContactContract.GroupDetailEntry.TABLE_NAME;

        sqLiteDatabase.execSQL(DROP_TABLE_CONTACT);
        sqLiteDatabase.execSQL(DROP_TABLE_GROUP);
        sqLiteDatabase.execSQL(DROP_TABLE_GROUP_DETAIL);

        onCreate(sqLiteDatabase);
    }

    public long insertRecord(Contact contact) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COL_CONTACT_NAME, contact.getName());
        values.put(ContactContract.ContactEntry.COL_CONTACT_EMAIl, contact.getEmail());
        values.put(ContactContract.ContactEntry.COL_CONTACT_PHONE, contact.getPhone());

        long newRowId = database.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);

        database.close();
        return newRowId;
    }

    public void insertGroupDetails(long grpIdx,ArrayList<Integer> contactsId){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<contactsId.size();i++) {
            values.put(ContactContract.GroupDetailEntry._ID, contactsId.get(i));
            values.put(ContactContract.GroupDetailEntry.COL_GROUP_ID, grpIdx);

            database.insert(ContactContract.GroupDetailEntry.TABLE_NAME,null,values);
        }
        database.close();
    }

    public long createGroup(String groupName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactContract.GroupEntry.COL_GROUP_NAME, groupName);

        long newRowId = database.insert(ContactContract.GroupEntry.TABLE_NAME, null, values);

        Log.v("id at group",newRowId+"");
        database.close();
        return newRowId;
    }

    public ArrayList<Contact> readContacts(){
        ArrayList<Contact> list = new ArrayList<>();
        String query = "SELECT * FROM "+ ContactContract.ContactEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Log.v("val from cursor",cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3));
                int id = Integer.parseInt(cursor.getString(0));

                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                Log.v("invalid ",id+" ");
                list.add(new Contact(id,name,email,phone));
            }while(cursor.moveToNext());
        }
        cursor.close();
        Log.v("LIST SIZE",list.size()+"");
        return list;

    }

    public ArrayList<Group> readGroups(){
        ArrayList<Group> list = new ArrayList<>();
        String query = "SELECT * FROM "+ ContactContract.GroupEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                long id = Long.parseLong(cursor.getString(0));
                String str = cursor.getString(1);
                Group grp = new Group(id,str);
                list.add(grp);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return list;

    }

    public Group readGroupInfo(long groupid){
        SQLiteDatabase database = this.getReadableDatabase();
        Group temp=new Group();
        temp.setGrpId(groupid);

        String query = "SELECT * FROM "+ ContactContract.GroupDetailEntry.TABLE_NAME  +
                " WHERE "+ ContactContract.GroupDetailEntry.COL_GROUP_ID+" = "+groupid;
        Cursor cursor = database.rawQuery(query,null);

        ArrayList<Contact> contacts = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                Contact contact = readContactDetails(id);
                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        temp.setMembers(contacts);
        cursor.close();
        return temp;
    }

    public Contact readContactDetails(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Contact contact = new Contact();
            Cursor cursor = database.rawQuery("SELECT * FROM " + ContactContract.ContactEntry.TABLE_NAME +
                    " WHERE " + ContactContract.ContactEntry._ID + " = " + id, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(1);
                    String email = cursor.getString(2);
                    String phone = cursor.getString(3);
                    contact.setName(name);
                    contact.setEmail(email);
                    contact.setPhone(phone);
                }
                cursor.close();
            }
        return contact;
    }



}
