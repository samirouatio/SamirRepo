package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses.WV_Helpers;

import java.net.MalformedURLException;
import java.net.URL;

public class MyHost {
    public static String Host_Getters(String url) throws MalformedURLException {
        return new URL(url).getHost();
    }
}
