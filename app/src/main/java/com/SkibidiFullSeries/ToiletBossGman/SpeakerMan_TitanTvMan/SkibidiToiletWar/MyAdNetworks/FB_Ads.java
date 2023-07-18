package com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyAdNetworks;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.Useful.Const;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FB_Ads {

    public interface AdIzOver{
        void onAdIzOver();
    }

    private AppCompatActivity myActivity;
    public AdView adView;

    public FB_Ads(AppCompatActivity myActivity) {
        this.myActivity = myActivity;
    }

    public void showBanner(RelativeLayout bannerContainer){
        adView = new AdView(myActivity, Const.Meta_Banner, AdSize.BANNER_HEIGHT_50);
        bannerContainer.addView(adView);
        bannerContainer.setVisibility(View.VISIBLE);
        adView.loadAd();
    }

    public void showInter(AdIzOver adIzOver){

        InterstitialAd interstitialAd = new InterstitialAd(myActivity, Const.Meta_Inter);
        //Listener
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                adIzOver.onAdIzOver();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                adIzOver.onAdIzOver();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
                interstitialAd.show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());


    }
}
