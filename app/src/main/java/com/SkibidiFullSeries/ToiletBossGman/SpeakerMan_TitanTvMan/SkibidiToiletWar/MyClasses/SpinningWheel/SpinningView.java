package com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.List;

public class SpinningView extends View {

    private RectF range = new RectF();
    private Paint archPaint, textPaint;
    private int padding, radius, center, spinningWheelBG, myImgPadding;
    private List<SpinningWheelItem> mSpinningWheelItems;
    private OnLuckyWheelReachTheTarget mOnLuckyWheelReachTheTarget;
    private OnSpinningListner onSpinningListner;

    public SpinningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initComponents() {
        //arc paint object
        archPaint = new Paint();
        archPaint.setAntiAlias(true);
        archPaint.setDither(true);
        //text paint object
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextSize(30);
        //rect rang of the arc
        range = new RectF(padding, padding, padding + radius, padding + radius);
    }


    private float getAngleOfIndexTarget(int target) {
        return (360 / mSpinningWheelItems.size()) * target;
    }

    public void setWheelBackgoundWheel(int wheelBackground) {
        spinningWheelBG = wheelBackground;
        invalidate();
    }

    public void setItemsImagePadding(int imagePadding) {
        myImgPadding = imagePadding;
        invalidate();
    }

    public void setWheelListener(OnLuckyWheelReachTheTarget onLuckyWheelReachTheTarget) {
        mOnLuckyWheelReachTheTarget = onLuckyWheelReachTheTarget;
    }


    public void addWheelItems(List<SpinningWheelItem> spinningWheelItems) {
        mSpinningWheelItems = spinningWheelItems;
        invalidate();
    }


    private void drawWheelBackground(Canvas canvas) {
        Paint backgroundPainter = new Paint();
        backgroundPainter.setAntiAlias(true);
        backgroundPainter.setDither(true);
        backgroundPainter.setColor(spinningWheelBG);
        canvas.drawCircle(center, center, center, backgroundPainter);
    }


    private void drawImage(Canvas canvas, float tempAngle, Bitmap bitmap) {
        //get every arc img width and angle
        int imgWidth = (radius / mSpinningWheelItems.size()) - myImgPadding;
        float angle = (float) ((tempAngle + 360 / mSpinningWheelItems.size() / 2) * Math.PI / 180);
        //calculate x and y
        int x = (int) (center + radius / 2 / 2 * Math.cos(angle));
        int y = (int) (center + radius / 2 / 2 * Math.sin(angle));
        //create arc to draw
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        //rotate main bitmap
        float px = rect.exactCenterX();
        float py = rect.exactCenterY();
        Matrix matrix = new Matrix();
        matrix.postTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
        matrix.postRotate(tempAngle + 120);
        matrix.postTranslate(px, py);
        canvas.drawBitmap(bitmap, matrix, new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG ));
        Log.d("sadsdsddssd" , bitmap.getWidth() + " : "+bitmap.getHeight());
        matrix.reset();
    }


    private void drawText(Canvas canvas, float tempAngle, float sweepAngle, String text) {
        Path path = new Path();
        path.addArc(range, tempAngle, sweepAngle);
        float textWidth = textPaint.measureText(text);
        int hOffset = (int) (radius * Math.PI / mSpinningWheelItems.size() / 2 - textWidth / 2);
        int vOffset = (radius / 2 / 3) - 3;
        canvas.drawTextOnPath(text, path, hOffset, vOffset, textPaint);
    }


    public void rotateWheelToTarget(int target) {

        float wheelItemCenter = 270 - getAngleOfIndexTarget(target) + (360 / mSpinningWheelItems.size()) / 2;
        int DEFAULT_ROTATION_TIME = 9000;
        animate().setInterpolator(new DecelerateInterpolator())
                .setDuration(DEFAULT_ROTATION_TIME)
                .rotation((360 * 15) + wheelItemCenter)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mOnLuckyWheelReachTheTarget != null) {
                            mOnLuckyWheelReachTheTarget.onReachTarget();
                        }
                        if (onSpinningListner != null) {
                            onSpinningListner.onFinishedSpinning();
                        }
                        clearAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }


    public void resetRotationLocationToZeroAngle(final int target) {
        animate().setDuration(0)
                .rotation(0).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rotateWheelToTarget(target);
                        clearAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWheelBackground(canvas);
        initComponents();

        float tempAngle = 0;
        float sweepAngle = 360 / mSpinningWheelItems.size();

        for (int i = 0; i < mSpinningWheelItems.size(); i++) {
            archPaint.setColor(mSpinningWheelItems.get(i).color);
            canvas.drawArc(range, tempAngle, sweepAngle, true, archPaint);
//            drawImage(canvas, tempAngle, mSpinningWheelItems.get(i).bitmap);
            drawText(canvas, tempAngle, sweepAngle, mSpinningWheelItems.get(i).text == null ? "" : mSpinningWheelItems.get(i).text);
            tempAngle += sweepAngle;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        int DEFAULT_PADDING = 5;
        padding = getPaddingLeft() == 0 ? DEFAULT_PADDING : getPaddingLeft();
        radius = width - padding * 2;
        center = width / 2;
        setMeasuredDimension(width, width);
    }

    public void setOnRotationListener(OnSpinningListner onSpinningListner) {
        this.onSpinningListner = onSpinningListner;
    }
}
