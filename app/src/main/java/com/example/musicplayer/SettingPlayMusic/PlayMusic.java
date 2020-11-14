package com.example.musicplayer.SettingPlayMusic;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.widget.SeekBar;

public class PlayMusic implements Runnable {
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private SeekBar mSeekBar;
    private String mMusicAdress;

    public PlayMusic(MediaPlayer mediaPlayer, Context context, String musicAddress) {
        mMediaPlayer=mediaPlayer;
        mContext=context.getApplicationContext();
        mMusicAdress =musicAddress;
    }

    public void playSong(SeekBar seekBar,MediaPlayer mediaPlayer) {

        mSeekBar=seekBar;
        boolean wasPlaying=false;
        try {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                clearMediaPlayer();
                seekBar.setProgress(0);
                wasPlaying = true;
            }


            if (!wasPlaying) {

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }

                AssetFileDescriptor descriptor = mContext.getAssets().openFd(mMusicAdress);
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());


                mediaPlayer.prepare();
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.setLooping(false);
                seekBar.setMax(mediaPlayer.getDuration());

                mediaPlayer.start();
                new Thread(this).start();

            }

            wasPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @Override
    public void run() {

        int currentPosition = mMediaPlayer.getCurrentPosition();
        int total = mMediaPlayer.getDuration();


        while (mMediaPlayer != null && mMediaPlayer.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = mMediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            mSeekBar.setProgress(currentPosition);

        }
    }

    public void clearMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
