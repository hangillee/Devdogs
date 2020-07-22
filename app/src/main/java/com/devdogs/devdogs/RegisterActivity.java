package com.devdogs.devdogs;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devdogs.devdogs.Retrofit.RegisterService;
import com.devdogs.devdogs.Retrofit.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText match_password;
    private EditText name;
    private Button registerButton;

    RegisterService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        match_password = findViewById(R.id.pw_register_matching);
        name = findViewById(R.id.name_register);
        registerButton = findViewById(R.id.button_register);

        service = RetrofitSingleton.getInstance().retrofit.create(RegisterService.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                String matchPassword = match_password.getText().toString().trim();
                String nameString = name.getText().toString().trim();

                if (emailString.isEmpty() || passwordString.isEmpty() || nameString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 값을 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                    Toast.makeText(getApplicationContext(), "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                } else if (!passwordString.equals(matchPassword)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    submit(emailString, passwordString, nameString);
                }
            }
        });
    }
    public void submit(String email, String password, String nameString){
        service.register(email, password, nameString).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "이미 가입되어 있는 이메일입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
