package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.PhotoOptionsAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.customViews.PreviewText;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.PhotoOptions;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.TextInfo;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.FileRepository;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.NotesRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView delete;
    private ImageView edit;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private FileRepository fileRepository;
    private NotesRepository notesRepository;
    private int deltaX;
    private int deltaY;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        image = findViewById(R.id.image);
        delete = findViewById(R.id.delete);
        edit = findViewById(R.id.edit);
        drawerLayout = findViewById(R.id.drawer_layout);
        listView = findViewById(R.id.listView);
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("uri");
        final File file = new File(str);
        Uri uri = Uri.fromFile(file);
        image.setImageURI(uri);
        Bitmap bitmap = null;
        try {
            InputStream stream = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(stream);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] byteArray = outputStream.toByteArray();

        ArrayList<PhotoOptions> options = new ArrayList<>();
        options.add(new PhotoOptions("@drawable/outline_font_download_black_18dp",
                "fonts"));
        options.add(new PhotoOptions("@drawable/baseline_cloud_upload_black_18dp",
                "upload to spec"));
        PhotoOptionsAdapter adapter = new PhotoOptionsAdapter(
                PhotoActivity.this,
                R.layout.photo_option_row,
                options,
                byteArray
        );
        listView.setAdapter(adapter);
        fileRepository = FileRepository.getInstance();
        notesRepository = new NotesRepository(PhotoActivity.this, "NotesKot.db",
                null, 1);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PhotoActivity.this);
                alert.setTitle("Usuwanie!");
                alert.setMessage("Usuwać?");
                alert.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Note note = notesRepository.get(file.toString());
                        if(note!=null) notesRepository.delete(note);
                        file.delete();
                        Intent intent = new Intent(PhotoActivity.this,AlbumsActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==300)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            Bundle extras = data.getExtras();
            TextInfo textInfo = (TextInfo)extras.getSerializable("textInfo");
            FrameLayout image = findViewById(R.id.theImage);
            final PreviewText text = new PreviewText(PhotoActivity.this,
                    Typeface.createFromAsset(getAssets(),
                            "fonts/"+textInfo.getFontName()),textInfo.getText(),
                    textInfo.getMainColor(),
                    textInfo.getEdgesColor());
            text.setLayoutParams(new RelativeLayout.LayoutParams((int)textInfo.getWidth()+1+30,
                    (int)textInfo.getHeight()+1+50));
            text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            int x = Math.round(motionEvent.getRawX());
                            int y = Math.round(motionEvent.getRawY()) - 100;
                            deltaX = x - ((int)text.getX());
                            deltaY = y - ((int)text.getY());
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int x2 = Math.round(motionEvent.getRawX());
                            int y2 = Math.round(motionEvent.getRawY()) - 100;
                            text.setX(x2-deltaX);
                            text.setY(y2-deltaY);
                            break;
                    }
                    return true;
                }
            });
            image.addView(text);
        }

    }
}
