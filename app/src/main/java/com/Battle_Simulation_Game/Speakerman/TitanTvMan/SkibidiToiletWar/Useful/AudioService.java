package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Useful;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyApp;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.R;

public class AudioService extends Service {

    public static MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        float volumeLevel = 0.4f;

        // Lower the volume of the AudioService
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * volumeLevel), 0);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int audioIndex;
        if (MyApp.audioIsAtTheStart) {
            audioIndex = R.raw.audio_middle;
        } else {
            audioIndex = R.raw.audio_start;
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), audioIndex);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        mediaPlayer.stop();
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    public static void pauseMusic() {
        if (AudioService.mediaPlayer != null && AudioService.mediaPlayer.isPlaying()) {
            AudioService.mediaPlayer.pause();
            MyApp.isPaused = true;
        }
    }

    public static void resumeMusic() {
        if (AudioService.mediaPlayer != null && MyApp.isPaused) {
            AudioService.mediaPlayer.start();
            MyApp.isPaused = false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
