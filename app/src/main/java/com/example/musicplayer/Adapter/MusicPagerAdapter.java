package com.example.musicplayer.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayer.Controller.Fragment.MusicsFragment;

public class MusicPagerAdapter extends FragmentStateAdapter {

    public static final int TAB_COUNT = 3;

    public MusicPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return MusicsFragment.newInstance("Singers");
            case 1:
               return MusicsFragment.newInstance("Albums");
            case 2:
                return MusicsFragment.newInstance("Songs");
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
