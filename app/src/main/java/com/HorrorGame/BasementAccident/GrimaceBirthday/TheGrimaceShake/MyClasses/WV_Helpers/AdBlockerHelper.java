package com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers;

import android.content.Context;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;

public class AdBlockerHelper {

    static Map<String, Boolean> The_Urls_Loader_Hash = new HashMap<>();

    public static boolean MainAdBlockerFunction(WebView view, String url) {
        boolean ad;
        if (!The_Urls_Loader_Hash.containsKey(url)) {
            ad = AdBlockerChecker.Ad_Checker(url);
            The_Urls_Loader_Hash.put(url, ad);
        } else {
            ad = The_Urls_Loader_Hash.get(url);
        }
        return ad;
    }

    public static class init {
        Context myContext;

        public init(Context myContext) {
            AdBlockerChecker.init(myContext);
            this.myContext = myContext;
        }

        public void Init(WebView mWebView) {


        }
    }
}
