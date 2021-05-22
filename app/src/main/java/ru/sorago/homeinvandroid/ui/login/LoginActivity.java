package ru.sorago.homeinvandroid.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sorago.homeinvandroid.MainActivity;
import ru.sorago.homeinvandroid.R;
import ru.sorago.homeinvandroid.api.ApiClient;
import ru.sorago.homeinvandroid.api.SessionManager;
import ru.sorago.homeinvandroid.core.MimeEncoder;
import ru.sorago.homeinvandroid.data.model.User;
import ru.sorago.homeinvandroid.data.request.LoginRequest;
import ru.sorago.homeinvandroid.data.response.base.RecordResponse;
import ru.sorago.homeinvandroid.data.response.type.LoginData;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            LoginRequest loginRequest = new LoginRequest(username, MimeEncoder.encode(password));
            ApiClient.getApiService().login(loginRequest)
                    .enqueue(new Callback<RecordResponse<LoginData>>() {
                        @Override
                        public void onFailure(Call<RecordResponse<LoginData>> call, Throwable t) {
                            // Error logging in
                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Nope, server ded", Toast.LENGTH_SHORT).show();
                            loadingProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onResponse(Call<RecordResponse<LoginData>> call, Response<RecordResponse<LoginData>> response) {
                            try {
                                RecordResponse<LoginData> loginResponse = response.body();
                                if (loginResponse != null && loginResponse.getData() != null) {
                                    User user = new User();
                                    user.setEmail(loginResponse.getData().getEmail());
                                    user.setName(loginResponse.getData().getName());
                                    SessionManager.saveAuthToken(getApplicationContext(), loginResponse.getData().getToken());
                                    updateUiWithUser(user);
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            loadingProgressBar.setVisibility(View.GONE);
                        }
                    });
        });
    }

    private void updateUiWithUser(User user) {
        String welcome = getString(R.string.welcome) + user.getName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        finish();
    }
}