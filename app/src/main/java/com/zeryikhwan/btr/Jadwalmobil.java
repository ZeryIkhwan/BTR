package com.zeryikhwan.btr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.model.Jadwal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zery Ikhwan on 6/25/2018.
 */

public class Jadwalmobil extends Fragment {

    //API_Service
    BaseApiService mApiService;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Jadwal Mobil Unit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jadwalmobil, container, false);
    }

    public void dataJadwal() {
        mApiService.jadwalRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        JSONArray jsonArray = jsonRESULTS.getJSONArray("jadwal");
                        Jadwal jadwal = new Jadwal();
                        ArrayList<Jadwal> jadwalArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            jadwal.setTempat(object.getString("tempat"));
                            jadwal.setAlamat(object.getString("alamat"));
                            jadwal.setTanggal(object.getString("tanggal"));
                            jadwal.setWaktu(object.getString("waktu"));
                        }
                        jadwalArrayList.add(jadwal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
