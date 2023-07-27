package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses.WV_Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyChrome extends WebChromeClient {
    private View myNewView;
    private WebChromeClient.CustomViewCallback myNewViewCallBack;
    protected FrameLayout mFullscreenContainer;
    private int DefaultOrientation;
    private int DefaultSystemVisibility;


    private AppCompatActivity mActivity;
    public MyChrome(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    public Bitmap getDefaultVideoPoster()
    {
        if (myNewView == null) {
            return null;
        }
        return BitmapFactory.decodeResource(mActivity.getResources(), 2130837573);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    public void onHideCustomView()
    {
        ((FrameLayout)mActivity.getWindow().getDecorView()).removeView(this.myNewView);
        this.myNewView = null;
        mActivity.getWindow().getDecorView().setSystemUiVisibility(this.DefaultSystemVisibility);
        mActivity.setRequestedOrientation(this.DefaultOrientation);
        this.myNewViewCallBack.onCustomViewHidden();
        this.myNewViewCallBack = null;
    }

    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
    {
        if (this.myNewView != null)
        {
            onHideCustomView();
            return;
        }
        this.myNewView = paramView;
        this.DefaultSystemVisibility = mActivity.getWindow().getDecorView().getSystemUiVisibility();
        this.DefaultOrientation = mActivity.getRequestedOrientation();
        this.myNewViewCallBack = paramCustomViewCallback;
        ((FrameLayout)mActivity.getWindow().getDecorView()).addView(this.myNewView, new FrameLayout.LayoutParams(-1, -1));
        mActivity.getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
