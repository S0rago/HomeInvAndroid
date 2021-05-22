package ru.sorago.homeinvandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.sorago.homeinvandroid.api.ApiClient;
import ru.sorago.homeinvandroid.ui.login.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final EditText linkEditText = findViewById(R.id.linkEdit);
        final Button linkButton = findViewById(R.id.linkBtn);

        linkButton.setOnClickListener(v -> {
            String link = linkEditText.getText().toString();
            ApiClient.setUrl((link.equals("") ? "http://10.0.2.2:8080" : link) + "/api/v1/");
            swapToLogin();
        });
    }
    private void swapToLogin() {
        Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        finish();
    }

}
