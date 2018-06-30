package com.zeryikhwan.btr.Action;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static final String CEK_LOGIN = "IsLogin";
    // Shared preferences file name
    public static final String PREF_NAME = "PREF_GUBOOK";
    private static final String CEK_SUDAH_LOGIN = "sessionLogin";
    private static final String CEK_SUDAH_SCAN = "sudahScan";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    public PrefManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    //Session_Login
    public boolean sessionLogin() {
        return pref.getBoolean(CEK_SUDAH_LOGIN, false);
    }

    public void setSudahLogin(boolean sudahLogin, String[] data) {
        editor.putBoolean(CEK_SUDAH_LOGIN, sudahLogin);

        //User_Pendonor
        editor.putString("email", data[0]);
        editor.putString("nama", data[1]);
        editor.putString("tgldonor", data[2]);
        editor.putString("jumdonor", data[3]);

        editor.commit();
    }

    // ini method khusus pemanggilan button logout
    // karena logout tidak memerlukan data user;
    public void setSudahLogin(boolean sudahLogin) {
        editor.putBoolean(CEK_SUDAH_LOGIN, sudahLogin);
        editor.commit();
    }

    //Session Sudah Scan

    public long getSudahScan() {
        return pref.getLong(CEK_SUDAH_SCAN, 0);
    }

    public void setSudahScan(long sudahScan) {
        editor.putLong(CEK_SUDAH_SCAN, sudahScan);
        editor.commit();
    }

    public String getDataNama() {
        return pref.getString("nama", "");
    }

    public String getDataEmail() {
        return pref.getString("email", "");
    }

    public String getDataJumlah() {
        return pref.getString("jumdonor", "");
    }

    public String getDataTanggal() {
        return pref.getString("tgldonor", "");
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

}
