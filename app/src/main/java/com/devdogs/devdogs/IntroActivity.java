package com.devdogs.devdogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devdogs.devdogs.Retrofit.LoginService;
import com.devdogs.devdogs.Retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity {
    LoginService service;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        /*SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        memes.clear().apply();
        memes.commit();*/

        RetrofitSingleton.getInstance(this);

        if (isLogin == true) {
            service = RetrofitSingleton.getInstance().retrofit.create(LoginService.class);
            this.submit("1", "1");
        }

        Button loginButton = findViewById(R.id.button_main_login);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        Button registerButton = findViewById(R.id.button_main_register);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
    public void submit(String email, String password){
        service.login(email, password).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent submitIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(submitIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "자동 로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
