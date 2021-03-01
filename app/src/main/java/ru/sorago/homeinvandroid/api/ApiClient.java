package ru.sorago.homeinvandroid.api;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static UserApi apiService;

    public static UserApi getApiService() {
        // Initialize ApiService if not initialized yet
        if (apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/api/v1/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            apiService = retrofit.create(UserApi.class);
        }
        return apiService;
    }
}
