package com.zeryikhwan.btr.apiAndroid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {

    //REGISTER
    @FormUrlEncoded
    @POST("apiregist.php")
    Call<ResponseBody> registerRequest(@Field("user_email") String email,
                                       @Field("user_password") String password,
                                       @Field("user_nama") String nama,
                                       @Field("user_alamat") String alamat,
                                       @Field("user_kecamatan") String kecamatan,
                                       @Field("user_pekerjaan") String pekerjaan,
                                       @Field("user_gender") String gender,
                                       @Field("user_tempatlahir") String tempatlahir,
                                       @Field("user_tgllahir") String tgllahir,
                                       @Field("user_suku") String suku,
                                       @Field("user_hp") String hp,
                                       @Field("user_tglakhirdonor") String tglakhirdonor,
                                       @Field("user_jumlahdonor") String jumlahdonor,
                                       @Field("user_goldarah") String goldarah,
                                       @Field("user_berat") String berat);

    //LOGIN
    @FormUrlEncoded
    @POST("apilogin.php")
    Call<ResponseBody> loginRequest(@Field("user_email") String email,
                                    @Field("user_password") String password);


    //JADWAL_MOBIL
    @GET("apijadwal.php")
    Call<ResponseBody> jadwalRequest();

}
