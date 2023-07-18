package com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.Useful;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.R;
import com.willy.ratingbar.ScaleRatingBar;


public class RatingBarDialog extends Dialog {

    private AppCompatActivity mContext;

    View decoration;


    public RatingBarDialog(@NonNull AppCompatActivity mContext) {
        super(mContext);
        this.mContext = mContext;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_dialogue);

        final TextView Rate = findViewById(R.id.rating);
        final TextView Ignore = findViewById(R.id.cancel);
        final ScaleRatingBar ratingBar= findViewById(R.id.simpleRatingBar);


        //This will Hide Nav And Status Bar
        decoration =getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility==0){
                decoration.setSystemUiVisibility(SystemBars());
            }
        });


        Rate.setOnClickListener(view -> {
            float score = ratingBar.getRating();
            if (score < 0) {
                Toast.makeText(mContext.getApplicationContext(), "needs to be more than 0", Toast.LENGTH_SHORT).show();
            } else if (score < 3) {
                Toast.makeText(mContext.getApplicationContext(), "Thanks for your help <3", Toast.LENGTH_SHORT).show();
                dismiss();
            } else if (score >= 3) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" +
                                mContext.getApplicationContext().getPackageName())));

                dismiss();
            }

        });

        Ignore.setOnClickListener(view -> {
            if(!Const.Rating_Is_Forced){
                dismiss();
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

    public int SystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }
}
