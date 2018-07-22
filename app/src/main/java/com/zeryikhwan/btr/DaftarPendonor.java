package com.zeryikhwan.btr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zeryikhwan.btr.adapter.DaftarAdapter;
import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.apiAndroid.UtilsApi;
import com.zeryikhwan.btr.model.Daftar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPendonor extends AppCompatActivity {

    //API_Service
    BaseApiService mApiService;

    private ArrayList<Daftar> daftarArrayList = new ArrayList<>();
    private DaftarAdapter daftarAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pendonor);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        mApiService = UtilsApi.getApiService();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplication(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataDaftar();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void dataDaftar() {
        mApiService.daftarRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        JSONArray jsonArray = jsonRESULTS.getJSONArray("user");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Daftar daftar = new Daftar();
                            daftar.setNama(object.getString("nama"));
                            daftar.setHp(object.getString("hp"));
                            daftar.setGolDarah(object.getString("gol_darah"));

                            daftarArrayList.add(daftar);
                            daftarAdapter = new DaftarAdapter(daftarArrayList);
                            daftarAdapter.notifyDataSetChanged();
                        }
                        recyclerView.setAdapter(daftarAdapter);


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
