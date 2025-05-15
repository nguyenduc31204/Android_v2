package com.example.newspaper.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.newspaper.R;
import com.example.newspaper.models.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        User user = getItem(position);

        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());

        return convertView;
    }
}