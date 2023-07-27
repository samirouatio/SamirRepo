package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Activities.RedActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Activities.FinalActivity;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Activities.SpinningActivity;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Activities.WalkthroughActivity;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses.MenuItems;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.R;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyAdNetworks.MyAdManager;
import com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.Useful.Const;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.RecyclerViewHolder> {

    Intent intent;
    Context context;
    AppCompatActivity activity;
    List<MenuItems> castItemsArrayList;

    Dialog adLoadingPopup;

    public MenuAdapter(List<MenuItems> castItemsArrayList, Context context, AppCompatActivity activity)
    {
        this.castItemsArrayList = castItemsArrayList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return castItemsArrayList.size() ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder castViewHolder, @SuppressLint("RecyclerView") int position)
    {
        final MenuItems model = castItemsArrayList.get(position);

        if (model.getName().equals("")){
            castViewHolder.name.setVisibility(View.GONE);
        }else{
            castViewHolder.name.setVisibility(View.VISIBLE);
            castViewHolder.name.setText(model.getName());
        }

        if (model.getIco().equals("")){
            castViewHolder.ico.setVisibility(View.GONE);

        }else{
            castViewHolder.ico.setVisibility(View.VISIBLE);
            castViewHolder.vector.setVisibility(View.VISIBLE);

            Glide
                    .with(context)
                    .load(model.getIco())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(castViewHolder.ico);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    castViewHolder.vector.animate().rotationBy(360)
                            .withEndAction(this).setDuration(5000)
                            .setInterpolator(new LinearInterpolator()).start();
                }
            };
            Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.main_bounce);
            castViewHolder.ico.setAnimation(zoomIn);
            castViewHolder.vector.animate().rotationBy(360).withEndAction(runnable).setDuration(8000).setInterpolator(new LinearInterpolator()).start();
        }

        castViewHolder.bg.setBackgroundColor(Color.parseColor(model.getBg()));

        //I have added this
        MyAdManager myAdManager = new MyAdManager(activity);
        myAdManager.adPicker();

        //The Loading Dialogue While Waiting For the Ads
        adLoadingPopup = new Dialog(context);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);

        castViewHolder.root.setOnClickListener(view -> {
            Animation hang_fall = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
            hang_fall.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    adLoadingPopup.show();
                    myAdManager.DisplayInter(new MyAdManager.ManagerAdIzOver() {
                        @Override
                        public void ManagerInterIsOver() {
                            adLoadingPopup.dismiss();
                            next(position);
                        }
                    });
//                    ANChooser.ShowInterstitial((Activity) context, true, ()->next(position));

//                    next(position);
                }
                public void onAnimationRepeat(Animation animation) { }
                public void onAnimationStart(Animation animation) { }
            });
            view.startAnimation(hang_fall);
        });
    }

    void next(int where){
//        Toast.makeText(context,"Go To Guide" + where,Toast.LENGTH_LONG).show();

        if (where == 0){
//            if (SplashActivity.activate_steps.equals("true")){
//                intent = new Intent(context, B_Second_Step.class);
//            }else{
//                intent = new Intent(context, MenuActivity.class);
//            }
            if(Const.WalkThrough_Skipped){
                intent = new Intent(context, FinalActivity.class);

            }
            else {
                intent = new Intent(context, WalkthroughActivity.class);

            }
        }
//        else if (where == 1){
////            intent = new Intent(context, PuzzleActivity.class);
//        }
        else if (where == 2){
            intent = new Intent(context, SpinningActivity.class);
        }
        else if (where == 3){
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(Const.URL_Privacy));
        }
        else if (where == 4){
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse(Const.URL_APP_PlayStore));
        }
        else {
            if(Const.WalkThrough_Skipped){
                intent = new Intent(context, FinalActivity.class);

            }
            else {
                intent = new Intent(context, WalkthroughActivity.class);

            }
        }
//        else if (where == 3){
//            intent = new Intent(context, ScratchingActivity.class);
//        }
//        else if (where == 4){
//            intent = new Intent(context, QuizzActivity.class);
//        }
//        else if (where == 5){
//            intent = new Intent(context, ImagesActivity.class);
//        }

        if(Const.Redirection_Activity_is_Active){
            intent = new Intent(context, RedActivity.class);
        }
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new RecyclerViewHolder(menuItemLayoutView);
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView ico, vector;
        LinearLayout root;
        View bg;

        public RecyclerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            vector = itemView.findViewById(R.id.vector);
            ico = itemView.findViewById(R.id.ico);
            root = itemView.findViewById(R.id.root);
            bg = itemView.findViewById(R.id.bg);
        }
    }
}
