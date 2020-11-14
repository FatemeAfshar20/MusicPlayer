package com.example.musicplayer.Adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {
    private final List<String> mMusicNameList = new ArrayList<>();
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private boolean isStarted = false;

    public List<String> getMusicNameList() {
        return mMusicNameList;
    }

    public void setMusicNameList(List<String> musicNameList) {
        this.mMusicNameList.clear();
        this.mMusicNameList.addAll(musicNameList);
        notifyDataSetChanged();
    }

    public MusicAdapter(Context context) {
        mContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.item_music,
                        parent,
                        false);

        return new Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mMusicNameList.get(position));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMediaPlayer(mMusicNameList.get(position));
                if (!isStarted){
                    mMediaPlayer.start();
                    isStarted=true;
                }else
                    mMediaPlayer.stop();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicNameList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createMediaPlayer(String path) {
        mMediaPlayer=new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(0.5f, 0.5f);
            mMediaPlayer.setLooping(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mMusicTitle;
        private ImageView mImgCover;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

            setListener(itemView);
        }

        private void setListener(@NonNull View itemView) {

        }

        private void findViews(@NonNull View itemView) {
            mMusicTitle = itemView.findViewById(R.id.music_name);
            mImgCover=itemView.findViewById(R.id.img_cover);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(String path) {
            mMusicTitle.setText(getMusicName(path));
            setImgMusicCover(path);
        }

        private void setImgMusicCover(String path) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            try {
                byte[] art = retriever.getEmbeddedPicture();
                Bitmap songImage = BitmapFactory
                        .decodeByteArray(art, 0, art.length);
                mImgCover.setImageBitmap(songImage);
            }catch (Exception e){
                mImgCover.setImageDrawable(mContext.
                        getResources().
                        getDrawable(R.drawable.download));
            }
        }

        private String getMusicName(String path) {
            String[] nameSection = path.split(File.separator);
            String fullMusicName = nameSection[nameSection.length - 1];
            return fullMusicName.substring(0, fullMusicName.lastIndexOf("."));
        }
    }
}
