package com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.Useful;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.CatMeme.BananaSeries.CatWhinySituation.BananaCatCrying.R;

public class InternetClassReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ConnectionCheckerClass.isMyGameConnected(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View noConnectionView = LayoutInflater.from(context).inflate(R.layout.retry_no_connection, null);
            builder.setView(noConnectionView);

            AppCompatButton tryAgainBtn = noConnectionView.findViewById(R.id.tryAgainBtn);

            //showDialogue
            AlertDialog noConnectionDialog = builder.create();
            noConnectionDialog.show();
            noConnectionDialog.setCancelable(false);


            noConnectionDialog.getWindow().setGravity(Gravity.CENTER);

            tryAgainBtn.setOnClickListener(view -> {
                noConnectionDialog.dismiss();
                onReceive(context, intent);
            });
        }
    }
}
