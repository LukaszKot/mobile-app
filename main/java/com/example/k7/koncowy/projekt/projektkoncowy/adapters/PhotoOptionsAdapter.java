package com.example.k7.koncowy.projekt.projektkoncowy.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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
import com.example.k7.koncowy.projekt.projektkoncowy.domain.PhotoOptions;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.UploadPhoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhotoOptionsAdapter extends ArrayAdapter {
    private ArrayList<PhotoOptions> _list;
    private AppCompatActivity appCompatActivity;
    private Context _context;
    private int _resource;
    private byte[] photo;

    public PhotoOptionsAdapter(@NonNull Context context, int resource, @NonNull List objects, byte[] photo) {
        super(context, resource, objects);
        this._list=(ArrayList<PhotoOptions>) objects;
        this.appCompatActivity = (AppCompatActivity) context;
        this._context = context;
        this._resource = resource;
        this.photo = photo;
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
                                    try
                                    {
                                        String result = new UploadPhoto(_context, photo).execute().get();
                                        AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                                        alert.setTitle("KOMUNIKAT SERWERA");
                                        alert.setMessage(result);
                                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        });
                                        alert.show();
                                    }
                                    catch (InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    catch (ExecutionException e)
                                    {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            alert.show();
                        }
                    });
                }
                else if(position==2)
                {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/jpeg");
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String d = df.format(new Date());
                    String tempFileName = d+"tymczasowy.jpg";
                    File sdCard = Environment.getExternalStorageDirectory();
                    File photoFile = new File(sdCard,tempFileName);
                    try
                    {
                        FileOutputStream fs = new FileOutputStream(photoFile);
                        fs.write(photo);
                        fs.close();
                    }catch (FileNotFoundException e){}
                    catch (IOException e){}
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/"+tempFileName));
                    _context.startActivity(Intent.createChooser(share,"Podziel się plikiem!"));
                }
            }
        });
        return convertView;
    }
}
