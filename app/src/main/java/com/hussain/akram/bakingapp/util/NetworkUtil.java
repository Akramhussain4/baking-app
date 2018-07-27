package com.hussain.akram.bakingapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.hussain.akram.bakingapp.activity.MainActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkUtil {

    private static final String TAG = NetworkUtil.class.getName();

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit = null;

    private NetworkUtil() {
    }

    public static Retrofit buildUrl() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetwork = false;
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connMgr != null) {
                networkInfo = connMgr.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnected()) {
                isNetwork = true;
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        return isNetwork;
    }

    public static void showNetworkAlert(final Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("No Network!")
                .setMessage("Check network and try again")
                .setPositiveButton("Try again!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkUtil.isNetworkAvailable(context);
                        if (!isNetworkAvailable(context)) {
                            showNetworkAlert(context);
                        } else {
                            dialog.dismiss();
                            ((MainActivity) context).checkNetwork();
                        }
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
