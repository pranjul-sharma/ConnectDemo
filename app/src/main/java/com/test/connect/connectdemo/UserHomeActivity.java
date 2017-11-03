package com.test.connect.connectdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static final int MY_PERMISSIONS_REQUEST_CONTACT = 123;

    private ViewPager mViewPager;
    private FloatingActionMenu fabMenu;
    private TabLayout tabLayout;
    private FloatingActionButton fabAddContact,fabAddGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);


        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group_black_24dp);

        fabMenu = (FloatingActionMenu)findViewById(R.id.fab_menu);
        fabAddContact = (FloatingActionButton)findViewById(R.id.fab_add_contact);
        fabAddGroup = (FloatingActionButton)findViewById(R.id.fab_add_group);

        final Intent intent = new Intent(UserHomeActivity.this,DetailSaveActivity.class);
        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("title",fabAddContact.getLabelText());
                startActivity(intent);
            }
        });

        fabAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("title",fabAddGroup.getLabelText());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs",MODE_PRIVATE);
        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_settings:
                Intent intent = new Intent(UserHomeActivity.this, StartActivity.class);
                Toast.makeText(getApplicationContext(), "User logged out.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                return true;
            case R.id.import_device:
                if (ContextCompat.checkSelfPermission(UserHomeActivity.this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(UserHomeActivity.this,Manifest.permission.READ_CONTACTS)) {
                       /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("allow permission to save contact from device.");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(UserHomeActivity.this,new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_CONTACT);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                       */
                        ActivityCompat.requestPermissions(UserHomeActivity.this,new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_CONTACT);

                    } else {
                        ActivityCompat.requestPermissions(UserHomeActivity.this,new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_CONTACT);
                    }
                } else {
                    Intent intent1 = new Intent(UserHomeActivity.this,ContactImportActivity.class);
                    startActivity(intent1);
                }
                return true;

            case R.id.import_facebook:
                if(!sharedPreferences.getBoolean("FACEBOOK_LOGGED_IN",false)){
                    //import from facebook code here
                    Toast.makeText(getApplicationContext(),"import fb code here",Toast.LENGTH_LONG).show();
                }else{
                    //request facebook login here
                    //and then import from facebook code here
                    Toast.makeText(getApplicationContext(),"login fb code here",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.import_google:
                if(!sharedPreferences.getBoolean("GOOGLE_LOGGED_IN",false)){
                    //import from facebook code here
                    Toast.makeText(getApplicationContext(),"import google code here",Toast.LENGTH_LONG).show();
                }else{
                    //request facebook login here
                    //and then import from facebook code here
                    Toast.makeText(getApplicationContext(),"login google code here",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.import_linkedin:
                if(!sharedPreferences.getBoolean("LINKEDIN_LOGGOD_IN",false)){
                    //import from facebook code here
                    Toast.makeText(getApplicationContext(),"import linked in code here",Toast.LENGTH_LONG).show();
                }else{
                    //request facebook login here
                    //and then import from facebook code here
                    Toast.makeText(getApplicationContext(),"login linkedin code here",Toast.LENGTH_LONG).show();
                }
                return true;
        }

        return false;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void setUpViewPager(ViewPager mViewPager){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(ContactListFragment.newInstance(),"Contacts");
        sectionsPagerAdapter.addFragment(GroupListFragment.newInstance(),"Groups");

        mViewPager.setAdapter(sectionsPagerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.v("tag 3","permission result");
        if (requestCode == MY_PERMISSIONS_REQUEST_CONTACT) {
            Log.v("tag 3.0","permission result code match "+MY_PERMISSIONS_REQUEST_CONTACT);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("tag 3.1","permission given");
                Intent intent = new Intent(UserHomeActivity.this,ContactImportActivity.class);
                startActivity(intent);
            } else {
                //code for deny
                Log.v("tag 3.2","permission denied");
                Toast.makeText(getApplicationContext(), "User denied to grant permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
