package com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.R;

import java.util.List;

public class SpinningWheel extends FrameLayout implements View.OnTouchListener, OnSpinningListner {
    private SpinningView spinningView;
    private ImageView arrowPointer;
    private int myTarget = -1;
    private boolean isSpinning = false;

    public SpinningWheel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeComponent();
        applyAttribute(attrs);
    }

    public SpinningWheel(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeComponent();
        applyAttribute(attrs);
    }

    private void initializeComponent() {
        inflate(getContext(), R.layout.lucky_wheel_layout, this);
        setOnTouchListener(this);
        spinningView = findViewById(R.id.wv_main_wheel);
        spinningView.setOnRotationListener(this);
        arrowPointer = findViewById(R.id.iv_arrow);
    }

    public void addSpinningWheelItems(List<SpinningWheelItem> spinningWheelItems) {
        spinningView.addWheelItems(spinningWheelItems);
    }

    public void applyAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LuckyWheel, 0, 0);
        try {
            int backgroundColor = typedArray.getColor(R.styleable.LuckyWheel_background_color, Color.WHITE);
            int arrowImage = typedArray.getResourceId(R.styleable.LuckyWheel_arrow_image, R.drawable.ic_pin2);
            int imagePadding = typedArray.getDimensionPixelSize(R.styleable.LuckyWheel_image_padding , 0);
            spinningView.setWheelBackgoundWheel(backgroundColor);
            spinningView.setItemsImagePadding(imagePadding);
            arrowPointer.setImageResource(arrowImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        typedArray.recycle();
    }

    public void setLuckyWheelReachTheTarget(OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget) {
        spinningView.setWheelListener(onLuckyWheelReachTheTarget);
    }

    public void setMyTarget(int myTarget) {
        this.myTarget = myTarget;
    }


    public void spinTheWheelTo(int number) {
        isSpinning = true;
        spinningView.resetRotationLocationToZeroAngle(number);
    }

    final int SWIPE_DISTANCE_THRESHOLD = 100;
    float x1, x2, y1, y2, dx, dy;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if ( myTarget < 0 || isSpinning) {
            return false;
        }

        switch (event.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                dx = x2 - x1;
                dy = y2 - y1;
                if ( Math.abs(dx) > Math.abs(dy) ) {
                    if ( dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD )
                        spinTheWheelTo(myTarget);
                } else {
                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD )
                        spinTheWheelTo(myTarget);
                }
                break;
            default:
                return true;
        }
        return true;
    }

    @Override
    public void onFinishedSpinning() {
        isSpinning = false;
    }
}
