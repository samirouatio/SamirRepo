package com.simulation.kingapp.learning.newtricks;

import android.app.Application;
import android.widget.Toast;

import com.simulation.kingapp.learning.newtricks.MyClasses.WalkthroughClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyApp extends Application {

    public static boolean audioIsPlaying = false;
    public static boolean isPaused = false;

    public static ArrayList<WalkthroughClass>  walkthroughClassArrayList =new ArrayList<>();
    public static Set<String> WV_EnablationArray =new HashSet<>();

    public static int FinishedGettingJsonData = 0;
    public static String PlayerName = "Player";

    @Override
    public void onCreate() {
        super.onCreate();
        try {




        }
        catch (Exception ex){
            Toast.makeText(MyApp.this,"Unexpected Error",Toast.LENGTH_LONG).show();
        }
    }




}
