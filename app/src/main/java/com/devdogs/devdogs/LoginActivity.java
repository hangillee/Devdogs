package com.devdogs.devdogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devdogs.devdogs.Retrofit.LoginService;
import com.devdogs.devdogs.Retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText name;
    private Button loginButton;

    LoginService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.button_login);

        service = RetrofitSingleton.getInstance().retrofit.create(LoginService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();

                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 값을 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    submit(emailString, passwordString);
                }
            }
        });
    }
    public void submit(String email, String password){
        service.login(email, password).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    IntroActivity.isLogin = true;
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent submitIntent = new Intent(getApplicationContext(), MainActivity.class);
                    submitIntent.addFlags(submitIntent.FLAG_ACTIVITY_CLEAR_TASK | submitIntent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(submitIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
