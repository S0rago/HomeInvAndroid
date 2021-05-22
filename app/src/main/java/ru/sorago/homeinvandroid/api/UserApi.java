package ru.sorago.homeinvandroid.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.sorago.homeinvandroid.data.request.ItemRequest;
import ru.sorago.homeinvandroid.data.request.LoginRequest;
import ru.sorago.homeinvandroid.data.response.base.ListResponse;
import ru.sorago.homeinvandroid.data.response.base.RecordResponse;
import ru.sorago.homeinvandroid.data.response.base.Response;
import ru.sorago.homeinvandroid.data.response.type.ItemData;
import ru.sorago.homeinvandroid.data.response.type.LoginData;

public interface UserApi {

    @POST("auth/register")
    Call<?> register();

    @POST("auth/login")
    Call<RecordResponse<LoginData>> login(@Body LoginRequest loginRequest);

    @POST("auth/logout")
    Call<?> logout();

    @GET("items/")
    Call<ListResponse<ItemData>> pullItems(@Header("Token") String token, @Query("offset") Integer offset, @Query("perPage") Integer perPage);

    @POST("items")
    Call<RecordResponse<ItemData>> addItem(@Header("Token") String token, @Body ItemRequest request);

    @DELETE("items/{id}")
    Call<?> deleteItem(@Header("Token") String token, @Query("id") Integer id);
}
