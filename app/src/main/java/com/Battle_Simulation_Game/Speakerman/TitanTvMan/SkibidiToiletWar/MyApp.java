package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar;

import android.app.Application;

import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses.WalkthroughClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyApp extends Application {

    public static boolean audioIsPlaying = false;
    public static boolean audioIsAtTheStart = false;
    public static boolean isPaused = false;

    public static ArrayList<WalkthroughClass> walkthroughClassArrayList = new ArrayList<>();
    public static Set<String> WV_EnablationArray = new HashSet<>();

    public static int FinishedGettingJsonData = 0;
    public static String PlayerName = "Player";

    public static boolean activityDestroyed = false;

    @Override
    public void onCreate() {
        super.onCreate();

    }


}
