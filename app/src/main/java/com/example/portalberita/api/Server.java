package com.example.portalberita.api;

public class Server {
    public static final String URL_API = "https://newsapi.org/";

    public static ApiInterface getApiService(){
        return ApiClient.getApiClient(URL_API).create(ApiInterface.class);
    }
}

