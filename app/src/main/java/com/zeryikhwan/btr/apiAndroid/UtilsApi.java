package com.zeryikhwan.btr.apiAndroid;

public class UtilsApi {

    public static final String BASE_URL_API = "http://zery.imukal.com/api/";

    //DEKLARASI INTERFACE BaseAPIService
    public static BaseApiService getApiService() {
        return AndroidClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
