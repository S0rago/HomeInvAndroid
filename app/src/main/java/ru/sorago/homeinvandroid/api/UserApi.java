package ru.sorago.homeinvandroid.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.sorago.homeinvandroid.data.request.LoginRequest;
import ru.sorago.homeinvandroid.data.response.LoginResponse;

public interface UserApi {

    @POST("auth/register")
    Call<?> register();

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/logout")
    Call<?> logout();

}
