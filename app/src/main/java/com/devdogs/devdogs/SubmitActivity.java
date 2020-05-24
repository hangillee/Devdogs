package com.devdogs.devdogs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devdogs.devdogs.retroit.SubmitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitActivity extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Button submitButton;

    Retrofit retrofit;
    Gson gson;
    SubmitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        title = findViewById(R.id.text_post_title);
        content = findViewById(R.id.text_post_content);
        submitButton = findViewById(R.id.button_post);

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + DATA.getURL() + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(SubmitService.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = title.getText().toString();
                String contentString = content.getText().toString();

                if (titleString.isEmpty() || contentString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 모두 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    submit(titleString, contentString);
                }
            }
        });
    }
    public void submit(String title, String content){
        service.submit(title, content).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
