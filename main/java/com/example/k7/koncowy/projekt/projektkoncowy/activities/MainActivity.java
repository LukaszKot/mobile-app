package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amitshekhar.DebugDB;
import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.FileRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LinearLayout albums;
    private FileRepository fileRepository;
    private LinearLayout camera;
    private LinearLayout notes;
    private LinearLayout network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileRepository = FileRepository.getInstance();
        albums = findViewById(R.id.albums);
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlbumsActivity.class);
                startActivity(intent);
            }
        });
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz źródło zdjęcia!");
                String[] opcje = {"aparat","galeria"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(intent.resolveActivity(getPackageManager())!=null){
                                startActivityForResult(intent, 0);
                            }
                        }
                        if(which==1)
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, 1);
                        }

                    }
                });
                alert.show();
            }
        });
        notes = findViewById(R.id.notes);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });

        network = findViewById(R.id.network);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) return;
        final Bitmap image;
        if(requestCode==0){
            Bundle extras = data.getExtras();
            image = (Bitmap)extras.get("data");
        }
        else if(requestCode==1) {
            Uri imgData = data.getData();
            InputStream stream;
            try
            {
                 stream = getContentResolver().openInputStream(imgData);
                 image = BitmapFactory.decodeStream(stream);
            }
            catch (FileNotFoundException e)
            {
                Log.d("xxx", e.getMessage());
                return;
            }

        } else return;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("W którym folderu zapisać to zdjęcie?");
        final String[] folders = fileRepository.getFolders();
        alert.setItems(folders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                fileRepository.saveFile(folders[which], byteArray);
            }
        });
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        alert.setView(imageView);
        alert.show();
    }
}
