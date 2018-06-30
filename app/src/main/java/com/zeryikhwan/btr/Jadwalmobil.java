package com.zeryikhwan.btr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zeryikhwan.btr.adapter.Jadwaladapter;
import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.apiAndroid.UtilsApi;
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

    ArrayList<Jadwal> jadwalArrayList = new ArrayList<>();
    private Jadwaladapter mAdapter;
    private RecyclerView recyclerView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Jadwal Mobil Unit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jadwalmobil, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mApiService = UtilsApi.getApiService();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataJadwal();
        return view;
    }

    public void dataJadwal() {
        mApiService.jadwalRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        JSONArray jsonArray = jsonRESULTS.getJSONArray("jadwal");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Jadwal jadwal = new Jadwal();
                            jadwal.setTempat(object.getString("tempat"));
                            jadwal.setAlamat(object.getString("alamat"));
                            jadwal.setTanggal(object.getString("tanggal"));
                            jadwal.setWaktu(object.getString("waktu"));

                            jadwalArrayList.add(jadwal);
                            mAdapter = new Jadwaladapter(jadwalArrayList);
                            mAdapter.notifyDataSetChanged();
                        }
                        recyclerView.setAdapter(mAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
