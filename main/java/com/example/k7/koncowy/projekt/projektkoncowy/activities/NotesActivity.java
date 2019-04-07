package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.NotesAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.adapters.PhotosAdapter;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.FileRepository;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.NotesRepository;

import java.io.File;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        listView = findViewById(R.id.listView);
        NotesRepository notesRepository = new NotesRepository(NotesActivity.this,
                "NotesKot.db", null, 1);
        ArrayList<Note> notesList = notesRepository.getAll();
        NotesAdapter adapter = new NotesAdapter(
                NotesActivity.this,
                R.layout.note_row,
                notesList
        );
        listView.setAdapter(adapter);
    }
}
