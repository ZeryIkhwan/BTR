package com.zeryikhwan.btr;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    ImageButton txtgl;
    EditText inptgl, inpuser, inppass, inpnama, inpalamat, inpkecamatan, inppekerjaan, inpsuku, inphp;
    private int year, month, day;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            inptgl.setText(dayOfMonth + "/" + month + "/" + year);
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
        inptgl = (EditText) findViewById(R.id.txTgllahir);


    }

    public void showDate(View view) {
        DatePickerDialog dpd = new DatePickerDialog(this, dateListener, year, month, day);
        dpd.show();

    }

}
