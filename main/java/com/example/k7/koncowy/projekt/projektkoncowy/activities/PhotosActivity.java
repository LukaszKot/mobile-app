package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.PhotosAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.FileRepository;

import java.io.File;
import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        listView = findViewById(R.id.listView);
        Bundle bundle = getIntent().getExtras();
        String folderName = bundle.getString("folderName");
        FileRepository fileRepository = FileRepository.getInstance();
        ArrayList<File> filesList = fileRepository.getFiles(folderName);
        PhotosAdapter adapter = new PhotosAdapter(
                PhotosActivity.this,
                R.layout.photo_row,
                filesList
        );
        listView.setAdapter(adapter);
    }
}
