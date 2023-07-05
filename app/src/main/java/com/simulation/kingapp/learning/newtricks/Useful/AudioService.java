package com.simulation.kingapp.learning.newtricks.Useful;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.simulation.kingapp.learning.newtricks.MyApp;
import com.simulation.kingapp.learning.newtricks.R;

public class AudioService extends Service {

    public static MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("myAudioLog","Starts Playing");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.awesomeness);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("myAudioLog","Stops  Playing");
        mediaPlayer.stop();
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("myAudioLog","Destryed  Playing");
//        mediaPlayer.pause();
        mediaPlayer.stop();
//        mediaPlayer.release();

    }

    public static void pauseMusic() {
        if (AudioService.mediaPlayer != null && AudioService.mediaPlayer .isPlaying()) {
            AudioService.mediaPlayer .pause();
            MyApp.isPaused = true;
        }
    }

    public static void resumeMusic() {
        if (AudioService.mediaPlayer  != null && MyApp.isPaused) {
            AudioService.mediaPlayer.start();
            MyApp.isPaused = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

