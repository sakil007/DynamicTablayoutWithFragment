
package pager.demo.com.fragmenttest;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentListActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    Context context;
    String[] values;
    ArrayList<String> valueList;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    int selectedTabPosition;
    private ActionBar actionBar;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list);
        context=getApplicationContext();
        values=getIntent().getStringArrayExtra("Values");
        List<String> l = Arrays.<String>asList(values);
        valueList=new ArrayList<>(l);
        getIDs();
        setEvents();
        setupTabLayout();

    }

    private void getIDs() {
        viewPager = (ViewPager) findViewById(R.id.my_viewpager);
        tabLayout = (TabLayout)findViewById(R.id.my_tab_layout);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), context, viewPager, tabLayout,mFragmentList,valueList);
        viewPager.setAdapter(adapter);
       // addPage
        for (int i=0;i<values.length;i++) {

            addPage(valueList.get(i));
        }

    }

    private void setEvents() {

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                Log.d("Selected", "Selected " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });
    }
    public void addPage(String pagename) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        //viewPager.setCurrentItem(adapter.getCount() - 1);
        setupTabLayout();
    }

    public void setupTabLayout() {
        selectedTabPosition = viewPager.getCurrentItem();

    //    tabLayout.getTabAt(0).setCustomView(adapter.getTabView(0));

     /* for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }*/
    }

}
