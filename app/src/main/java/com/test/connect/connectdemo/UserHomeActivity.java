package com.test.connect.connectdemo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ContactDBHelper db;
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
        db = new ContactDBHelper(getApplicationContext());
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(UserHomeActivity.this,StartActivity.class);
            Toast.makeText(getApplicationContext(),"User logged out.",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
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
}
