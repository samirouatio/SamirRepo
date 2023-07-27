package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.R;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Useful.Const;
import com.bumptech.glide.Glide;

public class RedActivity extends AppCompatActivity {

    ImageView theRedImg;
    AppCompatButton theRedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        theRedImg=findViewById(R.id.theRedImg);
        theRedButton=findViewById(R.id.theRedButton);


        Glide.with(this).load(Const.imgPreview).into(theRedImg);
        theRedButton.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_VIEW,  Uri.parse(Const.URL_Redirection));
            RedActivity.this.startActivity(intent);
        });
    }
}