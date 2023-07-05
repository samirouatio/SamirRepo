package com.simulation.kingapp.learning.newtricks.Useful;

import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RateMe extends Dialog {

    public  float ScoreRate =0;
    private AppCompatActivity mActivity;

    Dialog dialog;

    public RateMe(@NonNull AppCompatActivity activity) {
        super(activity);
        this.mActivity = activity;
    }



}
