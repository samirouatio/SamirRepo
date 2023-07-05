package com.simulation.kingapp.learning.newtricks.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simulation.kingapp.learning.newtricks.MyApp;
import com.simulation.kingapp.learning.newtricks.MyClasses.SecondClass;
import com.simulation.kingapp.learning.newtricks.R;
import com.simulation.kingapp.learning.newtricks.Useful.AudioService;
import com.simulation.kingapp.learning.newtricks.Useful.MyAdManager;
import com.bumptech.glide.Glide;
import com.yandex.mobile.ads.banner.BannerAdView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    MyAdManager myAdManager;
    ArrayList<SecondClass> SecondClassArray = new ArrayList<>();

    LinearLayout clickMe;

    TextView Question, Answer1,Answer2,Answer3;
    ImageView Iv1,Iv2,Iv3;
    int modelPosition = 0;

    private View decoration;

    //Banners
    RelativeLayout maxRLBanner;
    BannerAdView bannerAdViewYndx;

    //audio
    Intent serviceIntent;

    //loading
    private Dialog adLoadingPopup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        adLoadingPopup = new Dialog(SecondActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);


        bannerAdViewYndx = findViewById(R.id.yndxBanner);
        maxRLBanner = findViewById(R.id.generalBanner);


        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();
        myAdManager.DisplayBanner(maxRLBanner,bannerAdViewYndx);


        //This will Hide Nav And Status Bar
        decoration =getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0){
                    decoration.setSystemUiVisibility(SystemBars());
                }
            }
        });

        serviceIntent = new Intent(SecondActivity.this, AudioService.class);


        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();

        Question = findViewById(R.id.TextViewSelect);
        Answer1 = findViewById(R.id.Answer1);
        Answer2 = findViewById(R.id.Answer2);
        Answer3 = findViewById(R.id.Answer3);
        Iv1 = findViewById(R.id.myGif1);
        Iv2 = findViewById(R.id.myGif2);
        Iv3 = findViewById(R.id.myGif3);



        fillTheArrayModels();
        fillWithQuestions();
        clickMe = findViewById(R.id.clickMe);
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adLoadingPopup.show();
                myAdManager.DisplayInter(new MyAdManager.ManagerAdIzOver() {
                    @Override
                    public void ManagerInterIsOver() {
                        adLoadingPopup.dismiss();
                        nextQuestion();
                    }
                });
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decoration.setSystemUiVisibility(SystemBars());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(MyApp.audioIsPlaying){
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;
        }
    }

    public int SystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }


    private void fillWithQuestions(){
        SecondClass currentModel = SecondClassArray.get(modelPosition);
        Question.setText(currentModel.getQuestion());
        Answer1.setText(currentModel.getAnswer1());
        Answer2.setText(currentModel.getAnswer2());
        Answer3.setText(currentModel.getAnswer3());
        Glide.with(this).load(currentModel.getIv1()).circleCrop().into(Iv1);
        Glide.with(this).load(currentModel.getIv2()).circleCrop().into(Iv2);
        Glide.with(this).load(currentModel.getIv3()).circleCrop().into(Iv3);
    }
    private void nextQuestion(){
        if(modelPosition+1< SecondClassArray.size()){
            modelPosition++;
            fillWithQuestions();
        }
        else {
            Intent intent= new Intent(SecondActivity.this,FirstActivity.class);
            this.startActivity(intent);
            Toast.makeText(SecondActivity.this, "end" + SecondClassArray.size(),Toast.LENGTH_LONG).show();
        }

    }
    private void fillTheArrayModels() {
        SecondClass model1 = new SecondClass("How would you rate your gaming skills?","Beginner","Intermediate", "Expert", R.drawable.beginner,R.drawable.intermediate,R.drawable.expert);
        SecondClass model2 = new SecondClass("Which type of games are you more oriented Into?","Pixel art","Cartoon", "Realistic", R.drawable.pixel,R.drawable.cartoon,R.drawable.realistic);
        SecondClass model3 = new SecondClass("Which genre of games do you enjoy the most? ","Casual","Open-World", "Role-Play", R.drawable.pixel,R.drawable.cartoon,R.drawable.realistic);
        SecondClass model4 = new SecondClass("Select In-Game Language","English","Español", "Русский", R.drawable.uk,R.drawable.spain,R.drawable.russia);
        SecondClassArray.add(model1);
        SecondClassArray.add(model2);
        SecondClassArray.add(model3);
        SecondClassArray.add(model4);
    }

}