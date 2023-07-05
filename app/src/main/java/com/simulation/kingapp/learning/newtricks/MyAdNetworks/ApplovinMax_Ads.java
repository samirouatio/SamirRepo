package com.simulation.kingapp.learning.newtricks.MyAdNetworks;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.simulation.kingapp.learning.newtricks.R;
import com.simulation.kingapp.learning.newtricks.Useful.Const;

public class ApplovinMax_Ads   {

    //Native
    private MaxAd maxNative;

    //Banner
    private MaxAdView maxBanner;

    private final AppCompatActivity myActivity;

    public interface AdIzOver{
        void onAdIzOver();
    }

    public ApplovinMax_Ads(AppCompatActivity appCompatActivity){
        this.myActivity =appCompatActivity;
    }

    public void showFirstInter(final AdIzOver adIzOver){
        try {
            MaxInterstitialAd maxInterstitialAd = new MaxInterstitialAd(Const.Max_First, this.myActivity);
            maxInterstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    maxInterstitialAd.showAd();
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    adIzOver.onAdIzOver();
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    showMiddleInter(adIzOver);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    adIzOver.onAdIzOver();
                }
            });
            maxInterstitialAd.loadAd();
        }
        catch (Exception ThrowedException){
            showMiddleInter(adIzOver);
        }
    }

    public void showMiddleInter(AdIzOver adIzOver){
        try {

            MaxInterstitialAd maxInterstitialAd = new MaxInterstitialAd(Const.Max_Middle, this.myActivity);
            maxInterstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    maxInterstitialAd.showAd();
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    adIzOver.onAdIzOver();
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    showLastInter(adIzOver);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    adIzOver.onAdIzOver();
                }
            });
            maxInterstitialAd.loadAd();
        }
        catch (Exception ThrowedException){
            showLastInter(adIzOver);
        }
    }

    public void showLastInter(AdIzOver adIzOver){
        try {
            MaxInterstitialAd maxInterstitialAd = new MaxInterstitialAd(Const.Max_End, this.myActivity);
            maxInterstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    maxInterstitialAd.showAd();
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    adIzOver.onAdIzOver();
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    adIzOver.onAdIzOver();
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    adIzOver.onAdIzOver();
                }
            });
            maxInterstitialAd.loadAd();
        }
        catch (Exception ThrowedException){
            adIzOver.onAdIzOver();
        }
    }

    public void showBanner(RelativeLayout bannerContainer){
        maxBanner = new MaxAdView(Const.Max_Banner,myActivity);
        maxBanner.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd maxAd) {

            }

            @Override
            public void onAdCollapsed(MaxAd maxAd) {

            }

            @Override
            public void onAdLoaded(MaxAd maxAd) {
                int width= ViewGroup.LayoutParams.MATCH_PARENT;
                int height= myActivity.getResources().getDimensionPixelOffset(R.dimen.max_banner_height);

                if (maxBanner.getParent() != null) {
                    ((ViewGroup) maxBanner.getParent()).removeView(maxBanner);
                }

                maxBanner.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
                maxBanner.setBackgroundColor(ContextCompat.getColor(myActivity,R.color.white));
                bannerContainer.addView(maxBanner);
                bannerContainer.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(MaxAd maxAd) {

            }

            @Override
            public void onAdClicked(MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(String s, MaxError maxError) {
                Toast.makeText(myActivity,
                        "Banner onAdLoadFailed",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

            }
        });

        maxBanner.loadAd();

    }

    public void showNative(){

    }
}
