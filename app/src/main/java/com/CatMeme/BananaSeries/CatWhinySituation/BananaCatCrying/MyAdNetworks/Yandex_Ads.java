package com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.MyAdNetworks;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.Useful.Const;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

public class Yandex_Ads {
    public interface AdIzOver{
        void onAdIzOver();
    }
    private AppCompatActivity myActivity;

    public Yandex_Ads(AppCompatActivity myActivity) {
        this.myActivity = myActivity;
    }

    public void showBanner(BannerAdView bannerContainer){
        bannerContainer.setAdUnitId(Const.Yandeks_banner);
        bannerContainer.setVisibility(View.VISIBLE);
        bannerContainer.setAdSize(com.yandex.mobile.ads.banner.AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));

        final AdRequest adRequest=new AdRequest.Builder().build();

        bannerContainer.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        bannerContainer.loadAd(adRequest);

    }


    public void showInter(AdIzOver adIzOver){
        InterstitialAd interstitialAd=new InterstitialAd(myActivity);
        interstitialAd.setAdUnitId(Const.Yandeks_Inter);

        final AdRequest adRequest=new AdRequest.Builder().build();

        interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                adIzOver.onAdIzOver();

            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                adIzOver.onAdIzOver();
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        interstitialAd.loadAd(adRequest);

    }

}
