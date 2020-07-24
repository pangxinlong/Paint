package com.example.pangxinlong.paint.recycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.pangxinlong.paint.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/7/22
 * author: pangxinlong
 * Description:
 */
public class RecyclerActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        viewPager = findViewById(R.id.vp_recycle);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        initTabLayout();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.rootview, new RecyclerFragment());
//        fragmentTransaction.commit();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    TabText tabText = (TabText) tabLayout.getTabAt(position).getCustomView();
                    tabText.setLeftRate(positionOffset);

                    TabText nextText = (TabText) tabLayout.getTabAt(position + 1).getCustomView();
                    nextText.setRightRate(1 - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<ColorChangeTextView1> mTabs = new ArrayList<ColorChangeTextView1>();

    public void initTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TabText tabText = new TabText(this);
                tabText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tabText.setText(titles[i]);
                if (i == 0) {
                    tabText.setDefaultSelected();
                }
                tab.setCustomView(tabText);
            }
        }
    }

    private String[] titles = {"标题一", "标题二", "标题三"};

    class MyPagerAdapter extends FragmentStatePagerAdapter {


        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new RecyclerFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RecyclerActivity.class);
        context.startActivity(intent);
    }
}
