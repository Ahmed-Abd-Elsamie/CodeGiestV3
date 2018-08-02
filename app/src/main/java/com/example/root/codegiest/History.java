package com.example.root.codegiest;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class History extends AppCompatActivity {

    //Define the tabLayout
    private TabLayout tabLayout ;

    //Define the viewPager
    private ViewPager viewPager ;

    //Define an object from the HistoryPagerAdapter class
    private HistoryPagerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_history.xml layout file
        setContentView(R.layout.activity_history);


        //Select the tabLayout from activity_history.xml file
        tabLayout = (TabLayout) findViewById(R.id.history_tab_layout);

        //Select the viewPager from activity_history.xml file
        viewPager = (ViewPager) findViewById(R.id.history_view_pager);

        //Use the default constructor to define a new object
        adapter = new HistoryPagerAdapter(getSupportFragmentManager());

        //Add the two tabbed and give them their titels
        adapter.addFragment(new Complete() , "Complete");
        adapter.addFragment(new Going() , "Going");

        //Add our custom adapter to the viewPager
        viewPager.setAdapter(adapter);

        //Add the viewPager to our tabLayout
        tabLayout.setupWithViewPager(viewPager);


    }
}
