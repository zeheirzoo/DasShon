package com.example.project.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.models.Reserve;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<Reserve> {
    Context activity;
    ArrayList<Reserve> list;
    int resource;
    public NotificationAdapter(@NonNull Context context, int resource, ArrayList<Reserve> list) {
        super(context, resource,list);
        this.activity=context;
        this.resource=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(resource, parent, false);
        }
        TextView num_conception=convertView.findViewById(R.id.num_conception);
        num_conception.setText(getItem(position).getId_user()+"");
         return convertView;
    }
}
