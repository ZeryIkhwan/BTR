package com.zeryikhwan.btr;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.zeryikhwan.btr.Action.PrefManager;
import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.apiAndroid.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;


/**
 * Created by Zery Ikhwan on 6/25/2018.
 */

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private ZXingScannerView scannerView;
    PrefManager manager;
    BaseApiService apiService;
    Context mContext;
    //private
    private Calendar calendar, calendar2, calendar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        getSupportActionBar().hide();
        int currentApiVersion = Build.VERSION.SDK_INT;

        mContext = this;

        apiService = UtilsApi.getApiService();
        manager = new PrefManager(this);

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }


    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.KITKAT) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(Scanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        if (myResult.equals("BTR210697")) {
            if (manager.getDataJumlah() == null) {
                manager.setDataJumlah("0");
            }
            int i = 1;
            int jum = Integer.parseInt(manager.getDataJumlah());
            String jumlah = String.valueOf(jum + 1);
            manager.setDataJumlah(jumlah);


            //SET_WAKTU
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String formattedDate = df.format(c.getTime());
            manager.setDataTanggal(formattedDate);
            //END_SET WAKTU
            //Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

            apiService.scanRequset(manager.getDataId(), manager.getDataTanggal(), manager.getDataJumlah()).enqueue(new Callback<ResponseBody>() {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());

                            if (jsonRESULTS.getString("status").equals("success")) {
                                String id = jsonRESULTS.getJSONObject("user").getString("id");
                                String tgl_donor = jsonRESULTS.getJSONObject("user").getString("tgl_donor");
                                String jumlah = jsonRESULTS.getJSONObject("user").getString("jumlah_donor");

                                manager.setDataJumlah(jumlah);
                                manager.setDataTanggal(tgl_donor);


                                // Vibrate for 500 milliseconds
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    v.vibrate(500);
                                } else {
                                    //deprecated in API 26
                                    v.vibrate(500);
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(Scanner.this);
                                builder.setTitle("Terimakasih Telah Mendonorkan Darah Anda");
                                builder.setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Scanner.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                AlertDialog alert1 = builder.create();
                                alert1.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(mContext, "UPPSS .. \n\n Koneksi Internet Bermasalah \n\n Periksa kembali koneksi anda!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                v.vibrate(500);
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Maaf QR-Code yg anda scan, SALAH!");
            builder.setPositiveButton("Coba Lagi!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Scanner.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog alert1 = builder.create();
            alert1.show();
        }
    }

    //Exit Scan
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Scanner.this, Home.class);
        startActivity(intent);
        finish();
    }

}
