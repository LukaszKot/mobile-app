package com.example.k7.koncowy.projekt.projektkoncowy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.activities.PhotoActivity;
import com.example.k7.koncowy.projekt.projektkoncowy.alerts.NoteAlert;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.NotesRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends ArrayAdapter {

    private ArrayList<File> _list;
    private Context _context;
    private int _resource;
    private NotesRepository notesRepository;

    public PhotosAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this._list=(ArrayList<File>) objects;
        this._context = context;
        this._resource = resource;
        notesRepository = new NotesRepository(context,"NotesKot.db", null, 1);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(_resource, null);

        ImageView iv1 = convertView.findViewById(R.id.image);
        ImageView delete = convertView.findViewById(R.id.delete);
        ImageView edit = convertView.findViewById(R.id.edit);
        ImageView info = convertView.findViewById(R.id.info);
        final Uri uri = Uri.fromFile(_list.get(position));
        iv1.setImageURI(uri);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(_context, PhotoActivity.class);
                intent.putExtra("uri",_list.get(position).toString());
                _context.startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("Usuwanie!");
                alert.setMessage("Usuwać?");
                alert.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = _list.get(position);
                        Note note = notesRepository.get(file.toString());
                        if(note!=null) notesRepository.delete(note);
                        file.delete();
                        _list.remove(position);
                        notifyDataSetChanged();
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
                NoteAlert noteAlert = new NoteAlert(_context, _list.get(position), null);
                noteAlert.show();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("INFO");

                String message = "Info o zdjęciu: \n"+_list.get(position).toString();
                alert.setMessage(message)
                        .setNeutralButton("OK", null)
                        .show();

            }
        });

        return convertView;


    }
}
