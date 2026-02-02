package com.pdamsmg.pekerjaanteknik.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.pdamsmg.pekerjaanteknik.fitur.activity.home.HomeActivity;
import com.pdamsmg.pekerjaanteknik.fitur.activity.home.HomeSPKActivity;
import com.pdamsmg.pekerjaanteknik.fitur.activity.login.LoginActivity;

public final class ConfigAds {

    public static final String TAG = "debug";
    public static InterstitialAd mInterstitialAd;

    public final static String adsBanner =  "ca-app-pub-6810772781589252/9470804560";
//    public final static String adsBanner = "ca-app-pub-3940256099942544/6300978111"; // dev banner
//    public final static String adsInterestial = "ca-app-pub-6810772781589252/5659552068";
    public final static String adsInterestial = "ca-app-pub-3940256099942544/1033173712"; // dev interestial

    public static void interestial(Context context) {
        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // TODO definisi InterstitialAd
        InterstitialAd.load(context, adsInterestial, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded " + mInterstitialAd);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(((Activity) context));
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.d("TAG", "The ad was dismissed.");
//                            context.startActivity(new Intent(context, classJava));
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when fullscreen content failed to show.
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    public static void interestialIntent(Context context, Class classJava) {

        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // TODO definisi InterstitialAd
        InterstitialAd.load(context, adsInterestial, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.d(TAG, "onAdLoaded " + mInterstitialAd);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(((Activity) context));
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
                            Log.d("TAG", "The ad was dismissed.");
                            ((Activity) context).finishAffinity();
                            context.startActivity(new Intent(context, classJava));
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when fullscreen content failed to show.
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d(TAG, loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    public static void banner(Context context, AdView adView) {
        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
            Log.d(TAG, "banner: " + initializationStatus.getAdapterStatusMap());
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // banner
        AdView adViews = new AdView(context);
        adViews.setAdSize(AdSize.BANNER);
        adViews.setAdUnitId(adsBanner);
//         TODO call showAdsBanner
        adView.loadAd(adRequest);
    }

    public static void bannerIntent(Context context, AdView adView, Class to) {
        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
            Log.d(TAG, "banner: " + initializationStatus.getAdapterStatusMap());
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // banner
        AdView adViews = new AdView(context);
        adViews.setAdSize(AdSize.BANNER);
        adViews.setAdUnitId(adsBanner);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getResponseInfo());
                Toast.makeText(context, "Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "onAdLoaded:  succes");
                ((Activity) context).finishAffinity();
                context.startActivity(new Intent(context, to));
            }
        });
        // TODO call showAdsBanner
        adView.loadAd(adRequest);
    }

    public static void bannerIntentSharedPref(Context context, AdView adView) {
        // TODO inisialisasi MobileAds
        MobileAds.initialize(context, initializationStatus -> {
            Log.d(TAG, "banner: " + initializationStatus.getAdapterStatusMap());
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        // banner
        AdView adViews = new AdView(context);
        adViews.setAdSize(AdSize.BANNER);
        adViews.setAdUnitId(adsBanner);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                Log.d(TAG, "onAdFailedToLoad: " + loadAdError.getResponseInfo());
                Toast.makeText(context, "Cek Koneksi Internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "onAdLoaded:  succes");
                // App needs 10 MB within internal storage.
                new Handler().postDelayed(() -> {
                    SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, context.MODE_PRIVATE);
                    String telepon = sp.getString(Config.SHARED_NAME, "");
                    // TODO jika belum masuk ke LoginActivity
                    if (telepon.equalsIgnoreCase("") || TextUtils.isEmpty(telepon)) {
                        ((Activity) context).finishAffinity();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                    // TODO  jika sudah nantinya akan masuk ke Home
                    else {
                        ((Activity) context).finishAffinity();
                        context.startActivity(new Intent(context, HomeActivity.class));
                        Log.d("nohp", "run: " + telepon);
                    }
                }, 2000);
            }
        });
        // TODO call showAdsBanner
        adView.loadAd(adRequest);
    }

    public static void bannerAndInterestial(Context context, AdView adView) {
        banner(context, adView);
        interestial(context);
    }
}
