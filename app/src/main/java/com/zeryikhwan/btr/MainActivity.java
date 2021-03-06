package com.zeryikhwan.btr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeryikhwan.btr.Action.PrefManager;
import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.apiAndroid.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //API_Service
    BaseApiService mApiService;

    Handler handler;

    Context mContext;
    TextView regist;
    EditText inpemail, inppass;
    CardView login;
    PrefManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar myActionbar = getSupportActionBar();

        myActionbar.hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DaftarPendonor.class);
                startActivity(intent);
                finish();
            }
        });

        //API_Serviice
        mApiService = UtilsApi.getApiService();
        handler = new Handler();
        manager = new PrefManager(this);

        mContext = this;

        inpemail = (EditText) findViewById(R.id.txEmail);
        inppass = (EditText) findViewById(R.id.txPassword);

        regist = (TextView) findViewById(R.id.reg);
        login = (CardView) findViewById(R.id.cardView2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inpemail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inppass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    requestLogin();
                }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Yakin Ingin Keluar ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void requestLogin() {
        mApiService.loginRequest(inpemail.getText().toString(), inppass.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());

                        //Jika Sukses
                        if (jsonRESULTS.getString("status").equals("success")) {

                            //Parsing Data
                            String id = jsonRESULTS.getJSONObject("user").getString("id");
                            String email = jsonRESULTS.getJSONObject("user").getString("email");
                            String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                            String tgldonor = jsonRESULTS.getJSONObject("user").getString("tgl_akhirdonor");
                            String jumdonor = jsonRESULTS.getJSONObject("user").getString("jumlah_donor");
                            String hp = jsonRESULTS.getJSONObject("user").getString("hp");
                            String alamat = jsonRESULTS.getJSONObject("user").getString("alamat");
                            String gender = jsonRESULTS.getJSONObject("user").getString("gender");

                            manager.setSudahLogin(true, new String[]{id, email, nama, tgldonor, jumdonor, hp, alamat, gender});

                            Toast.makeText(mContext, "Login Sukses", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();


                        } else {
                            //Jika Login Gagal
                            Toast.makeText(getApplicationContext(), "Email Or Password Invalid", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "UPPSS .. \n\n Koneksi Internet Bermasalah \n\n Periksa kembali koneksi anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
