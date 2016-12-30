package com.myself.mysmarttablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String lastTile;
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    ArrayList<FragmentInfo> pages;
    private SubjectAdapter mNewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_tab);
        pages = new ArrayList<FragmentInfo>();

        FrameLayout layout = (FrameLayout) findViewById(R.id.tabs);
        layout.addView(View.inflate(this, R.layout.include_tab, null));
        mSmartTabLayout = (SmartTabLayout)findViewById(R.id.smart_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //需要显示的

        String[] titles;
            titles = getResources().getStringArray(R.array.news_titles);
            StringBuilder builder = new StringBuilder();
            for(int i = 0 ;i<titles.length;i++){
                builder.append(titles[i]);
                if(i!=titles.length-1){
                    builder.append("-");
                }
            }
            //获取到上次显示的标题
            lastTile = builder.toString();
        for (int i = 0; i < titles.length; i++) {
            FragmentInfo info;
            if (i == 0) {
                info = new FragmentInfo(new EmptyFragment(), titles[i]);
            } else {
                info = new FragmentInfo(new EmptyFragment(), titles[i]);
            }
            pages.add(info);
        }
        mNewAdapter = new SubjectAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(mNewAdapter);

        //!!!!关键代码,自动绑定数据
        mSmartTabLayout.setViewPager(mViewPager);
        mSmartTabLayout.setDividerColors(Color.TRANSPARENT);
    }
    public  class SubjectAdapter extends FragmentStatePagerAdapter {
        ArrayList<FragmentInfo> mFragments;

        public SubjectAdapter(FragmentManager fm, ArrayList<FragmentInfo> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        //需要刷新ViewPager的话,我们必须重写getItemPosition->POSITION_NONE
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  mFragments.get(position).getTitle();
        }

        public void setDate(ArrayList<FragmentInfo> mFragments){
            this.mFragments = mFragments;
            notifyDataSetChanged();
        }
    }
}
