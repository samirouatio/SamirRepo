package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses.WV_Helpers;

import android.graphics.Bitmap;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Useful.Const;

public class MyBrowser extends WebViewClient {

    WebView myGameView;
    public MyBrowser(WebView myGameView) {
        this.myGameView = myGameView;
    }

    @SuppressWarnings("deprecation")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if(Const.WV_Ad_Block_Is_Active){
            if(Const.Evaluate_JavaScript_IsActive){
                try {
                    String javascriptCode = Const.Function;
                    myGameView.evaluateJavascript(javascriptCode, null);
                }
                catch (Exception ex){

                }
            }
            return !AdBlockerHelper.MainAdBlockerFunction(view,url) ? AdBlockerChecker.responseToTheAd() :
                    super.shouldInterceptRequest(view, url);
        }

        return super.shouldInterceptRequest(view, url);
    }



    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(Const.Evaluate_JavaScript_IsActive){
            try {
                String javascriptCode = Const.Function;
                myGameView.evaluateJavascript(javascriptCode, null);
            }
            catch (Exception ex){

            }
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(Const.Evaluate_JavaScript_IsActive){
            try {
                String javascriptCode = Const.Function;
                myGameView.evaluateJavascript(javascriptCode, null);
            }
            catch (Exception ex){

            }
        }

        super.onPageFinished(view, url);

    }



    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }
}
