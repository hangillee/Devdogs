package com.devdogs.devdogs.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devdogs.devdogs.R;
import com.devdogs.devdogs.retroit.Submit;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Submit> {

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Submit> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;

        if (root == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            root = inflater.inflate(R.layout.item_submit, parent, false);
        }
        final Submit submit = this.getItem(position);
        if (submit != null){
            final TextView titleText = root.findViewById(R.id.text_list_title);
            final TextView authorText = root.findViewById(R.id.text_list_author);

            titleText.setText(submit.getTitle());
            authorText.setText(submit.getAuthor());
        }

        return root;
    }
}
