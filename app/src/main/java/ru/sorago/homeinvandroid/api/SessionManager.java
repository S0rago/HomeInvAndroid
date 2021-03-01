package ru.sorago.homeinvandroid.api;

import android.content.Context;
import android.content.SharedPreferences;
import ru.sorago.homeinvandroid.R;

public class SessionManager {
    public static void saveAuthToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences("HomeInvAndroid", Context.MODE_PRIVATE).edit();
        editor.putString("user_token", token);
        editor.apply();
    }

    public static String fetchAuthToken(Context context) {
        return context.getSharedPreferences("HomeInvAndroid", Context.MODE_PRIVATE).getString("user_token", null);
    }
}