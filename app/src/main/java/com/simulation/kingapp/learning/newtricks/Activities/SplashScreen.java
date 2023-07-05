package com.simulation.kingapp.learning.newtricks.Activities;

import static com.simulation.kingapp.learning.newtricks.MyApp.walkthroughClassArrayList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.simulation.kingapp.learning.newtricks.MyApp;
import com.simulation.kingapp.learning.newtricks.MyClasses.WalkthroughClass;
import com.simulation.kingapp.learning.newtricks.R;
import com.simulation.kingapp.learning.newtricks.Useful.Const;
import com.facebook.ads.AudienceNetworkAds;
import com.yandex.mobile.ads.common.InitializationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_DURATION = 1500; // 6 seconds

    private ProgressBar progressBar;
    private Handler PB_Handler;

    RequestQueue requestQueueIp;
    RequestQueue requestQueueJson;

    int check1 = 0;
    int check2 = 0;

    int IpCheckerIsFinished = 0;

    public String MyJsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        PB_Handler = new Handler();

        MyJsonUrl = getString(R.string.MyJsonUrl);

        requestQueueIp = Volley.newRequestQueue(this);
        requestQueueJson = Volley.newRequestQueue(this);

        // Start updating the progress bar
        getData();
        startProgressBar();


        Handler HandlerCheckEvery500MS = new Handler();
        HandlerCheckEvery500MS.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MyApp.FinishedGettingJsonData == 0 || IpCheckerIsFinished == 0){
                    HandlerCheckEvery500MS.postDelayed(this,SPLASH_DURATION);
                }
                else if(MyApp.FinishedGettingJsonData == 1 && IpCheckerIsFinished == 1){

                    InitializeAds();

                    Handler WaitForInitHandler =new Handler();
                    WaitForInitHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GoToSpecifiedActivity();

                        }
                    },SPLASH_DURATION);
                }
                else if(MyApp.FinishedGettingJsonData == 2 && IpCheckerIsFinished == 2){
                    Toast.makeText(SplashScreen.this,"Error : Connection Failed",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        },300);
    }



    public void getData(){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, MyJsonUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray walkthroughArray=response.getJSONArray("Walkthrough");
                            JSONArray myAppConfigArray=response.getJSONArray("My_App_Config");
                            JSONArray adNetworkArray =response.getJSONArray("Ad_Network");
                            JSONArray myNewAppArray=response.getJSONArray("My_New_App");
                            JSONArray wv_enablationArray=response.getJSONArray("WV_Enablation");


                            for(int i=0;i<walkthroughArray.length();i++){
                                JSONObject step=walkthroughArray.getJSONObject(i);
                                String titre=step.getString("titre");
                                String description=step.getString("description");
                                String image=step.getString("image");
                                WalkthroughClass theGuideModel =new WalkthroughClass(titre,description,image);
                                walkthroughClassArrayList.add(theGuideModel);
                            }



                            JSONObject AppConfig= myAppConfigArray.getJSONObject(0);



                            Const.URL_Privacy = AppConfig.getString("URL_Privacy");
                            Const.URL_APP_PlayStore = AppConfig.getString("URL_APP_PlayStore");
                            Const.Steps_Skipped = AppConfig.getBoolean("Steps_Skipped");
                            Const.WalkThrough_Skipped = AppConfig.getBoolean("WalkThrough_Skipped");
                            Const.WV_Ad_Block_Is_Active = AppConfig.getBoolean("WV_Ad_Block_Is_Active");
                            Const.IP_Checker_Is_Active = AppConfig.getBoolean("IP_Checker_Is_Active");
                            Const.WV_Support_Multiple_Windows_Active = AppConfig.getBoolean("WV_Support_Multiple_Windows_Active");
                            Const.Rating_Is_Forced = AppConfig.getBoolean("Rating_Is_Forced");
                            Const.Rating_Message = AppConfig.getString("Rating_Message");
                            Const.Rating_Timer_To_PopUp = AppConfig.getInt("Rating_Timer_To_PopUp");
                            Const.CPA_Is_Active = AppConfig.getBoolean("CPA_Is_Active");
                            Const.CPA_titre = AppConfig.getString("CPA_titre");
                            Const.CPA_Description = AppConfig.getString("CPA_Description");
                            Const.CPA_URL = AppConfig.getString("CPA_URL");
                            Const.CPA_Timer_To_Pop_Up = AppConfig.getInt("CPA_Timer_To_Pop_Up");
                            Const.Type_Of_MainActivity = AppConfig.getString("Type_Of_MainActivity");
                            Const.Video_Url = AppConfig.getString("Video_Url");
                            Const.URL_Webview_Google = AppConfig.getString("URL_Webview_Google");
                            Const.URL_Webview_User = AppConfig.getString("URL_Webview_User");
                            Const.Evaluate_JavaScript_IsActive = AppConfig.getBoolean("Evaluate_JavaScript_IsActive");
                            Const.Function = AppConfig.getString("Function");




                            JSONObject myAppConfigJsonObject= adNetworkArray.getJSONObject(0);


                            Const.My_Current_Ad_Network = myAppConfigJsonObject.getString("My_Current_Ad_Network");
                            Const.MaxInterPositionIndex = myAppConfigJsonObject.getInt( "Max_Inter_Index");
                            Const.Ads_Timer = myAppConfigJsonObject.getInt( "Ads_Timer");


                            //max
                            Const.Max_First = myAppConfigJsonObject.getString("Max_First");
                            Const.Max_Middle = myAppConfigJsonObject.getString("Max_Middle");
                            Const.Max_End = myAppConfigJsonObject.getString("Max_End");
                            Const.Max_Banner = myAppConfigJsonObject.getString("Max_Banner");
                            Const.Max_Native = myAppConfigJsonObject.getString("Max_Native");

                            //fb
                            Const.Meta_Inter = myAppConfigJsonObject.getString("Meta_Inter");
                            Const.Meta_Banner = myAppConfigJsonObject.getString("Meta_Banner");
                            Const.Meta_Native = myAppConfigJsonObject.getString("Meta_Native");

                            //yandex
                            Const.Yandeks_Inter = myAppConfigJsonObject.getString("Yandeks_Inter");
                            Const.Yandeks_banner = myAppConfigJsonObject.getString("Yandeks_banner");

                            JSONObject myNewAppJsonObject= myNewAppArray.getJSONObject(0);

                            //Custom Redirect Activity
                            Const.imgPreview = myNewAppJsonObject.getString("img");
                            Const.text_btn = myNewAppJsonObject.getString("text_btn");
                            Const.URL_Redirection = myNewAppJsonObject.getString("URL_Redirection");
                            Const.Redirection_Activity_is_Active = myNewAppJsonObject.getBoolean("Redirection_Activity_is_Active");



                            for (int i = 0; i < wv_enablationArray.length(); i++) {
                                String link = wv_enablationArray.getString(i);
                                MyApp.WV_EnablationArray.add(link);
                            }



                            MyApp.FinishedGettingJsonData =1;

                            if(Const.IP_Checker_Is_Active){
                                CheckTheIp();
                            }
                            else {
                                IpCheckerIsFinished = 1;
                            }


//                            if(DuhVariables.DuhBGActive){
//                                SimplePermissionChecker();
//                            }
//                            else {
//                                checkSelectedAd();
//                                Handler FinalHandler=new Handler();
//                                FinalHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        SimpleSkippingChecker();
//                                    }
//                                },6000);
//                            }


                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyApp.FinishedGettingJsonData =2;

            }
        });

        request.setShouldCache(false);
        requestQueueJson.add(request);
    }


    public void GoToSpecifiedActivity() {
        Intent intent;
        if (Const.Steps_Skipped) {
            intent = new Intent(SplashScreen.this, PlayerName.class);
        } else {
            intent = new Intent(SplashScreen.this, FirstActivity.class);
        }
        startActivity(intent);
        finish();
    }


    public void CheckTheIp(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://ipinfo.io/json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String TheHost= "";
                String TheOrganisation = "";
                try {
                    TheHost = response.getString("hostname");
                    TheHost = TheHost.toLowerCase();
                    check1 = 1;
                }

                catch (JSONException e) {
                    IpCheckerIsFinished = 2;

                }

                try {
                    TheOrganisation = response.getString("org");
                    TheOrganisation = TheOrganisation.toLowerCase();
                    check2 = 1;
                }
                catch (JSONException e){
                    IpCheckerIsFinished = 2;
                }

                int check3 = check1 + check2;

                if(check3 == 2 || check3 == 1){
                    IpCheckerIsFinished = 1;
                }

                if(IpCheckerIsFinished == 1){
                    if(TheHost.contains("google") || TheOrganisation.contains("google")) {
                        //TODO Deactivate Spam
                        Const.My_Current_Ad_Network = "";


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                IpCheckerIsFinished = 2;
            }
        });

        request.setShouldCache(false);
        requestQueueIp.add(request);
    }



    private void startProgressBar() {
        int totalProgress = 100;
        int increment = 1;
        long delay = (SPLASH_DURATION + SPLASH_DURATION) / totalProgress;

        Runnable runnable = new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                progress += increment;
                if (progress > totalProgress) {
                    progress = totalProgress;
                }

                progressBar.setProgress(progress);

                if (progress < totalProgress) {
                    PB_Handler.postDelayed(this, delay);
                }
            }
        };

        PB_Handler.postDelayed(runnable, delay);
    }

    public void InitializeAds(){

        //MaxAds Init
        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(AppLovinSdkConfiguration appLovinSdkConfiguration) {

            }
        });

        //Fb Meta Init
        AudienceNetworkAds.initialize(this);

        com.yandex.mobile.ads.common.MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {

            }
        });

    }
}