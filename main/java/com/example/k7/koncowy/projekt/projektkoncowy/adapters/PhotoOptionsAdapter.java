package com.example.k7.koncowy.projekt.projektkoncowy.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.activities.LettersActivity;
import com.example.k7.koncowy.projekt.projektkoncowy.alerts.CheckInternetAccessAlert;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.ICallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Network;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.PhotoOptions;

import java.util.ArrayList;
import java.util.List;

public class PhotoOptionsAdapter extends ArrayAdapter {
    private ArrayList<PhotoOptions> _list;
    private AppCompatActivity appCompatActivity;
    private Context _context;
    private int _resource;

    public PhotoOptionsAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this._list=(ArrayList<PhotoOptions>) objects;
        this.appCompatActivity = (AppCompatActivity) context;
        this._context = context;
        this._resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);
        ImageView image = convertView.findViewById(R.id.image);
        int imageResource = _context.getResources()
                .getIdentifier(_list.get(position).getImgSrc(), null, appCompatActivity.getPackageName());
        Drawable res = _context.getResources().getDrawable(imageResource);
        image.setImageDrawable(res);


        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(_list.get(position).getName());

        LinearLayout linearLayout = convertView.findViewById(R.id.row);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                    Intent intent = new Intent(appCompatActivity, LettersActivity.class);
                    appCompatActivity.startActivityForResult(intent,200);
                }
                else if(position==1)
                {
                    CheckInternetAccessAlert alert = new CheckInternetAccessAlert(_context);
                    alert.enforceInternetAccess(new ICallback() {
                        @Override
                        public void whenProcessDone() {
                            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                            alert.setTitle("Upload to Spec");
                            alert.setMessage("Wysłać na spec-a?");
                            alert.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.show();
                        }
                    });
                }
            }
        });
        return convertView;
    }
}
