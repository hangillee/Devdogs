package com.devdogs.devdogs.ui.home;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devdogs.devdogs.MainActivity;
import com.devdogs.devdogs.R;
import com.devdogs.devdogs.retroit.Submit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView listView = root.findViewById(R.id.list_home);
        postAdapter = new PostAdapter(container.getContext(), R.layout.item_submit, new ArrayList<Submit>());
        listView.setAdapter(postAdapter);

        getAllPosts();

        HashSet<String> abc= (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("PREF_COOKIES", new HashSet<String>());
        Log.e("VOVOKVKOOF", abc.toString());

        return root;
    }

    public void addSubmitFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_home, new SubmitFragment()).addToBackStack("submit");
        ft.commit();
    }

    public void getAllPosts() {
        MainActivity.service.getList().enqueue(new Callback<List<Submit>>() {
            @Override
            public void onResponse(Call<List<Submit>> call, Response<List<Submit>> response) {
                postAdapter.addAll(response.body());

            }

            @Override
            public void onFailure(Call<List<Submit>> call, Throwable t) {

            }
        });
    }
}