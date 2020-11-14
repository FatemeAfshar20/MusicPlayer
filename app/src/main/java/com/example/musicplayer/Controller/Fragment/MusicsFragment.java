package com.example.musicplayer.Controller.Fragment;

import android.Manifest;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Adapter.MusicAdapter;
import com.example.musicplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MusicsFragment extends Fragment {
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final int PERMISSION_COUNT = 1;
    public static final int REQUEST_PERMISSIONS = randomNumber(100, 500);

    private RecyclerView mRecyclerView;

    private MusicAdapter mMusicAdapter;
    private List<String> mMusicNameList = new ArrayList<>();

    public MusicsFragment() {
        // Required empty public constructor
    }

    public static MusicsFragment newInstance() {
        MusicsFragment fragment = new MusicsFragment();
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
        View view= inflater.inflate(
                R.layout.recycler_view,
                container,
                false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        mRecyclerView=view.findViewById(R.id.recycler_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (arePermissionDenied()) {
            ((ActivityManager) (getActivity().getSystemService(getActivity().ACTIVITY_SERVICE))).clearApplicationUserData();
            getActivity().recreate();
        } else
            onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }else {
            if (mMusicNameList.size()==0) {
                fillMusicList();
                setupAdapter();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean arePermissionDenied() {
        for (int i = 0; i < PERMISSION_COUNT; i++) {
            if (getActivity().checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED)
                return true;
        }
        return false;
    }

    private void setupAdapter() {
        mMusicAdapter=new MusicAdapter(getContext());
        mMusicAdapter.setMusicNameList(mMusicNameList);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMusicAdapter);
    }

    private List<String> addMusicFilesFrom(String path) {
        List<String> musicNames = new ArrayList<>();
        File musicDir = new File(path);

        if (musicDir.exists()) {
            File[] files = musicDir.listFiles();

            for (File file : files) {
                String musicPath = file.getAbsolutePath();
                if (musicPath.endsWith(".mp3"))
                    musicNames.add(musicPath);
            }
        }
        return musicNames;
    }

    private void fillMusicList() {
        mMusicNameList.addAll(addMusicFilesFrom
                (String.valueOf(Environment.
                        getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_MUSIC))));

        mMusicNameList.addAll(addMusicFilesFrom
                (String.valueOf(Environment.
                        getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS))));
    }

    public static int randomNumber(int min, int max) {
        return (int)
                Math.abs(Math.random() * (max - min + 1) + min);
    }
}
