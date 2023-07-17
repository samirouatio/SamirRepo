package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel;

import android.graphics.Bitmap;

public class SpinningWheelItem {

    public int color;
    public Bitmap bitmap;
    public String text;

    public SpinningWheelItem(int color, Bitmap bitmap) {
        this.color = color;
        this.bitmap = bitmap;
    }

    public SpinningWheelItem(int color, Bitmap bitmap, String text) {
        this.color = color;
        this.bitmap = bitmap;
        this.text = text;
    }

}
