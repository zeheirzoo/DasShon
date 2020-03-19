package com.example.project.ui.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.R;

import java.util.ArrayList;

public class GridProductAdapter extends ArrayAdapter<Bitmap> {
    Context context;
    ArrayList<Bitmap> arrayList;
    public GridProductAdapter(@NonNull Context context, int resource,ArrayList<Bitmap> bitmaps) {
        super(context, resource);
        this.context=context;
        this.arrayList=bitmaps;
    }


    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;

        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }
        imageView = convertView.findViewById(R.id.imageView);
        ImageButton  delete =convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("supprimer");
                builder.setMessage("supprimer");
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("supprimer ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.remove(getItem(position));
                        remove(getItem(position));
                        Toast.makeText(context, "deleted !!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();


            }
        });
            imageView.setImageBitmap(getItem(position));

        return convertView;

    }


}
