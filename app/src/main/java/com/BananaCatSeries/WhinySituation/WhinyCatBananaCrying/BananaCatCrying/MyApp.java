package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses.WalkthroughClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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

    public static int randomColor;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_FIRST_LAUNCH = "first_launch";
    @Override
    public void onCreate() {
        super.onCreate();

        boolean isFirstLaunch = isFirstLaunch();
        if (isFirstLaunch) {
            randomColor = getRandomColor();
            markFirstLaunch();
        }



    }

    private boolean isFirstLaunch() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(KEY_FIRST_LAUNCH, true);
    }

    private void markFirstLaunch() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_FIRST_LAUNCH, false);
        editor.apply();
    }



    public int getRandomColor() {
        Random rnd = new Random();

        // Define the minimum threshold for each RGB component (adjust these values as desired)
        int minRed = 50;    // Minimum red value (0 to 255)
        int minGreen = 50;  // Minimum green value (0 to 255)
        int minBlue = 50;   // Minimum blue value (0 to 255)

        int red = rnd.nextInt(256 - minRed) + minRed;
        int green = rnd.nextInt(256 - minGreen) + minGreen;
        int blue = rnd.nextInt(256 - minBlue) + minBlue;

        return Color.argb(255, red, green, blue);
    }


}
