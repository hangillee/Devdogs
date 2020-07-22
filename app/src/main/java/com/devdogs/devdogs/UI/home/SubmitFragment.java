package com.devdogs.devdogs.UI.home;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devdogs.devdogs.MainActivity;
import com.devdogs.devdogs.R;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitFragment extends Fragment {
    private EditText title;
    private EditText content;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_submit, container, false);

        title = root.findViewById(R.id.text_post_title);
        content = root.findViewById(R.id.text_post_content);
        submitButton = root.findViewById(R.id.button_post);

        HashSet<String> abc= (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("PREF_COOKIES", new HashSet<String>());
        Log.e("VOVOKVKOOF", abc.toString());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = title.getText().toString();
                String contentString = content.getText().toString();

                if (titleString.isEmpty() || contentString.isEmpty()) {
                    Toast.makeText(getContext().getApplicationContext(), "제목과 내용을 모두 입력해야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    submit(titleString, contentString);
                }
            }
        });

        return root;
    }

    public void submit(String title, String content){
        MainActivity.service.submit(title, content).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext().getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext().getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext().getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
