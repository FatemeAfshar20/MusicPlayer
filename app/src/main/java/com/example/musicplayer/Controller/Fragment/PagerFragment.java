package com.example.musicplayer.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musicplayer.Adapter.MusicPagerAdapter;
import com.example.musicplayer.R;
import com.google.android.material.tabs.TabLayout;

public class PagerFragment extends Fragment {
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_pager,
                container,
                false);
        findViews(view);
        setupAdapter();
        setupTabLayout();
        return view;
    }

    private void findViews(View view) {
        mTabLayout = view.findViewById(R.id.page_tab_layout);
        mViewPager2 = view.findViewById(R.id.view_pager);
    }

    private void setupAdapter() {
        MusicPagerAdapter viewPagerAdapter =
                new MusicPagerAdapter(getActivity());
        mViewPager2.setAdapter(viewPagerAdapter);
    }

    private void setupTabLayout() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}