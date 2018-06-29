package com.zeryikhwan.btr;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zeryikhwan.btr.apiAndroid.BaseApiService;
import com.zeryikhwan.btr.apiAndroid.UtilsApi;

import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    //API_Service
    BaseApiService mApiService;

    CardView btnRegist;
    Context mContext;
    ProgressDialog loading;

    ImageButton txtgl;
    EditText inptgl, inpemail, inppass, inpnama, inpalamat, inpkecamatan, inppekerjaan, inpsuku, inphp, inpberat, inptempatlahir;
    RadioGroup RgGol, RgGender;
    String Rbgender, Rbgoldar;
    private int year, month, day;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            inptgl.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    };

    //    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        calendar = Calendar.getInstance(Locale.getDefault());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        txtgl = (ImageButton) findViewById(R.id.btntgl);

        RgGol = (RadioGroup) findViewById(R.id.golDarah);
        RgGender = (RadioGroup) findViewById(R.id.Gender);

        inptgl = (EditText) findViewById(R.id.txTgllahir);
        inpemail = (EditText) findViewById(R.id.txEmail);
        inppass = (EditText) findViewById(R.id.txPassword);
        inpnama = (EditText) findViewById(R.id.txNama);
        inpalamat = (EditText) findViewById(R.id.txAlamat);
        inpkecamatan = (EditText) findViewById(R.id.txKecamatan);
        inppekerjaan = (EditText) findViewById(R.id.txPekerjaan);
        inpsuku = (EditText) findViewById(R.id.txSuku);
        inphp = (EditText) findViewById(R.id.txHp);
        inpberat = (EditText) findViewById(R.id.txBerat);
        inptempatlahir = (EditText) findViewById(R.id.txTempatlahir);

        btnRegist = (CardView) findViewById(R.id.btnRegist);
        //API_Serviice
        mApiService = UtilsApi.getApiService();

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inpemail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inppass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inpnama.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inpalamat.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inpkecamatan.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kecamatan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inppekerjaan.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pekerjaan tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (Rbgender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Gender tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inptempatlahir.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tempat Lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inptgl.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tanggal Lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inpsuku.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Suku tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inphp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Hp tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (Rbgoldar.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Gol Darah tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (inpberat.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Berat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }
            }
        });

        RgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (RgGender.getCheckedRadioButtonId()) {
                    case R.id.laki:
                        Rbgender = "Laki-laki";
                        break;
                    case R.id.wanita:
                        Rbgender = "Wanita";
                        break;
                }
            }
        });

        RgGol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (RgGol.getCheckedRadioButtonId()) {
                    case R.id.golA:
                        Rbgoldar = "A";
                        break;
                    case R.id.golB:
                        Rbgoldar = "B";
                        break;
                    case R.id.golAB:
                        Rbgoldar = "AB";
                        break;
                    case R.id.golO:
                        Rbgoldar = "O";
                        break;
                }
            }
        });

    }

    private void register() {
        mApiService.registerRequest(inpemail.getText().toString(),
                inppass.getText().toString(),
                inpnama.getText().toString(),
                inpalamat.getText().toString(),
                inpkecamatan.getText().toString(),
                inppekerjaan.getText().toString(),
                Rbgender,
                inptempatlahir.getText().toString(),
                inptgl.getText().toString(),
                inpsuku.getText().toString(),
                inphp.getText().toString(),
                //Default_Values Karena Saat Register Orang Tidak Bisa Langsung Scan Mendonor
                "0000-00-00",
                "0",
                Rbgoldar,
                inpberat.getText().toString())
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Register Sukses", Toast.LENGTH_SHORT).show();
                            finish();


                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "UPPSS .. \n\n Koneksi Internet Bermasalah \n\n Periksa kembali koneksi anda!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void showDate(View view) {
        DatePickerDialog dpd = new DatePickerDialog(this, dateListener, year, month, day);
        dpd.show();
    }
}
