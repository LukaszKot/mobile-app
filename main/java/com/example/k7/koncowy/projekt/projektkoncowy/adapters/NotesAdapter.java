package com.example.k7.koncowy.projekt.projektkoncowy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.alerts.NoteAlert;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.ICallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.NotesRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesAdapter extends ArrayAdapter {

    private ArrayList<Note> _list;
    private Context _context;
    private int _resource;
    private NotesRepository notesRepository;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this._list = (ArrayList<Note>) objects;
        this._context = context;
        this._resource = resource;
        notesRepository = new NotesRepository(context, "NotesKot.db", null, 1);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);

        TextView noteId = convertView.findViewById(R.id.noteId);
        TextView noteTitle = convertView.findViewById(R.id.noteTitle);
        TextView noteContent = convertView.findViewById(R.id.noteContent);
        TextView noteUrl = convertView.findViewById(R.id.noteUrl);
        LinearLayout theNote = convertView.findViewById(R.id.theNote);
        Note note = _list.get(position);
        noteId.setText(String.valueOf(note.getId()));
        noteTitle.setText(note.getTitle());
        noteTitle.setTextColor(Color.parseColor(NoteAlert.getColors().get(note.getColor())));
        noteContent.setText(note.getContent());
        noteUrl.setText(note.getImgUrl());
        theNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("Uwaga!");
                String[] opcje = {"edytuj", "usuń", "sortuj wg tytułu", "sortuj wg koloru"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0) {
                            File file = new File(_list.get(position).getImgUrl());
                            NoteAlert noteAlert = new NoteAlert(_context, file, new ICallback() {
                                @Override
                                public void whenProcessDone() {
                                    _list = notesRepository.getAll();
                                    notifyDataSetChanged();
                                }
                            });
                            noteAlert.show();
                        }
                        else if(which==1)
                        {
                            Note note = _list.get(position);
                            notesRepository.delete(note);
                            _list.remove(note);
                        }
                        else if(which==2)
                        {
                            Collections.sort(_list, new Comparator<Note>() {
                                @Override
                                public int compare(Note note, Note t1) {
                                    return note.getTitle().compareTo(t1.getTitle());
                                }
                            });
                        }
                        else
                        {
                            Collections.sort(_list, new Comparator<Note>() {
                                @Override
                                public int compare(Note note, Note t1) {
                                    if(note.getColor()>t1.getColor())
                                    {
                                        return 1;
                                    }
                                    if(note.getColor()==t1.getColor())
                                    {
                                        return 0;
                                    }
                                    return -1;
                                }
                            });
                        }
                        notifyDataSetChanged();
                    }
                });
                alert.show();
            }
        });

        return convertView;
    }
}
