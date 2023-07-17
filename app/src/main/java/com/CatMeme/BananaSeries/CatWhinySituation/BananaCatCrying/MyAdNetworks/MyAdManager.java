package com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.MyAdNetworks;

import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.Useful.Const;
import com.yandex.mobile.ads.banner.BannerAdView;

public class MyAdManager {

    private AppCompatActivity mActivity;


    public MyAdManager(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    ApplovinMax_Ads applovinMax_ads;
    FB_Ads fb_ads;
    Yandex_Ads yandex_ads;

    public interface ManagerAdIzOver {
        void ManagerInterIsOver();
    }

    public void adPicker(){
        if (Const.My_Current_Ad_Network.equals("max")) {
            applovinMax_ads = new ApplovinMax_Ads(mActivity);
        }
        else if (Const.My_Current_Ad_Network.equals("fb")){
            fb_ads = new FB_Ads(mActivity);
        }
        else if (Const.My_Current_Ad_Network.equals("yandex")){
            yandex_ads = new Yandex_Ads(mActivity);
        }

    }



    public void DisplayInter(ManagerAdIzOver managerAdIzOver){
        if (Const.My_Current_Ad_Network.equals("max")) {
            myMaxInterSpecifier(managerAdIzOver);
        }
        else if (Const.My_Current_Ad_Network.equals("fb")){
            fb_ads.showInter(() -> managerAdIzOver.ManagerInterIsOver());
        }
        else if (Const.My_Current_Ad_Network.equals("yandex")){
            yandex_ads.showInter(() -> managerAdIzOver.ManagerInterIsOver());
        }
        else {
            managerAdIzOver.ManagerInterIsOver();
        }
    }

    public void myMaxInterSpecifier(ManagerAdIzOver managerAdIzOver){

        if(Const.MaxInterPositionIndex == 1){
            applovinMax_ads.showFirstInter(() -> managerAdIzOver.ManagerInterIsOver());
        }
        else if(Const.MaxInterPositionIndex == 2){
            applovinMax_ads.showMiddleInter(() -> managerAdIzOver.ManagerInterIsOver());
        }
        else if(Const.MaxInterPositionIndex == 3){
            applovinMax_ads.showLastInter(() -> managerAdIzOver.ManagerInterIsOver());
        }
        else {
            applovinMax_ads.showLastInter(() -> managerAdIzOver.ManagerInterIsOver());
        }

    }

    public void DisplayBanner(RelativeLayout relativeLayoutBanner, BannerAdView bannerAdViewYandex){
        if (Const.My_Current_Ad_Network.equals("max")) {
            applovinMax_ads.showBanner(relativeLayoutBanner);
        }
        else if (Const.My_Current_Ad_Network.equals("fb")){
            fb_ads.showBanner(relativeLayoutBanner);
        }
        else if (Const.My_Current_Ad_Network.equals("yandex")){
            yandex_ads.showBanner(bannerAdViewYandex);
        }
        else {

        }
    }

}
