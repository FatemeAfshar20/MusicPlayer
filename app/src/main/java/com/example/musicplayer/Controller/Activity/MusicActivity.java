package com.example.musicplayer.Controller.Activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.Controller.Fragment.MusicsFragment;
import com.example.musicplayer.SingleFragmentActivity;

public class MusicActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MusicActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    public Fragment getFragment() {
        return MusicsFragment.newInstance();
    }

}