package com.zeryikhwan.btr.Action;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.MediaType;

public class Koneksi {

    private final String URL = "bukutamu-via.cloud.revoluz.io";
    private final MediaType Json = MediaType.parse("application/json");
    SharedPreferences sharedPreferences;
    PrefManager manager;
    String[] data;
    private String tesUrl;

    public Koneksi(Context context) {
        manager = new PrefManager(context);
        sharedPreferences = context.getSharedPreferences(manager.PREF_NAME, 0);
    }


}
