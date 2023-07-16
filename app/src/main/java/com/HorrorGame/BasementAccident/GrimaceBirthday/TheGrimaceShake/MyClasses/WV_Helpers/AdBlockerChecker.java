package com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceResponse;

import androidx.annotation.WorkerThread;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

public class AdBlockerChecker {

    private static final String My_Txt_File_In_Raw = "hosttheads.txt";
    public static final Set<String> Main_List_Of_Ads = new HashSet<>();

    public static void init(final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    loadFromAssets(context);
                } catch (IOException e) {
                    // noop
                }
                return null;
            }
        }.execute();
    }

    @WorkerThread
    private static void loadFromAssets(Context context) throws IOException {
        InputStream stream = context.getAssets().open(My_Txt_File_In_Raw);
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null){
            Main_List_Of_Ads.add(line);
        }
        bufferedReader.close();
        inputStreamReader.close();
        stream.close();
    }

    public static boolean Ad_Checker(String url) {
        try {
            return Should_We_Add_It_To_The_Host(MyHost.Host_Getters(url));
        } catch (MalformedURLException e) {
            Log.d("AdevX", e.toString());
            return false;
        }

    }

    private static boolean Should_We_Add_It_To_The_Host(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        int ind3x = host.indexOf(".");
        return ind3x >= 0 && (Main_List_Of_Ads.contains(host) ||
                ind3x + 1 < host.length() && Should_We_Add_It_To_The_Host(host.substring(ind3x + 1)));
    }

    public static WebResourceResponse responseToTheAd() {
        return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
    }

}
