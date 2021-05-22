package ru.sorago.homeinvandroid.api;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static UserApi apiService;
    private static String url = "http://127.0.0.1";
    public static UserApi getApiService() {
        // Initialize ApiService if not initialized yet
        if (apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            apiService = retrofit.create(UserApi.class);
        }
        return apiService;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ApiClient.url = url;
    }
}
