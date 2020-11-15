package com.example.musicplayer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class SingersAdapter extends RecyclerView.Adapter<SingersAdapter.Holder> {
    private List<String> mSingerNames=new ArrayList<>();
    private Context mContext;

    public List<String> getSingerNames() {
        return mSingerNames;
    }

    public void setSingerNames(List<String> singerNames) {
        mSingerNames.clear();
        mSingerNames.addAll(singerNames);
        notifyDataSetChanged();
    }

    public SingersAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).
                inflate(
                        R.layout.item_singer,
                        parent,
                        false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            holder.bind(mSingerNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mSingerNames.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private ImageView mSingerImg;
        private TextView mSingerName;
        private MediaMetadataRetriever mMetadataRetriever;

        public Holder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            mSingerImg=itemView.findViewById(R.id.img_singer);
            mSingerName=itemView.findViewById(R.id.singer_name);
        }

        public void bind(String path){
            mMetadataRetriever = new MediaMetadataRetriever();
            mMetadataRetriever.setDataSource(path);

            try {
                byte[] art = mMetadataRetriever.getEmbeddedPicture();
                Bitmap songImage = BitmapFactory
                        .decodeByteArray(art, 0, art.length);
                mSingerImg.setImageBitmap(songImage);
                mSingerName.setText(mMetadataRetriever.
                        extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            }catch (Exception e){
                mSingerImg.setImageDrawable(mContext.
                        getResources().
                        getDrawable(R.drawable.download));
            }
        }
    }
}
