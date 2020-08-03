package com.devdogs.devdogs.UI.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.devdogs.devdogs.R;
import com.devdogs.devdogs.Retrofit.RetrofitSingleton;
import com.devdogs.devdogs.Retrofit.Settings;
import com.devdogs.devdogs.Retrofit.SettingsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {
    private SettingsViewModel settingsViewModel;
    private TextView textView;
    private SettingsService service = RetrofitSingleton.getInstance().retrofit.create(SettingsService.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        textView = root.findViewById(R.id.text_share);
        getSettings();
        return root;
    }

    private void getSettings() {
        service.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.code() == 200){
                    final Settings settings = response.body();
                    if (settings.isAuthorized()){
                        textView.setText("Already Authorized.");
                    }
                }
                else {
                    Toast.makeText(getContext().getApplicationContext(), "Something wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext().getApplicationContext(), "Please Login.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}