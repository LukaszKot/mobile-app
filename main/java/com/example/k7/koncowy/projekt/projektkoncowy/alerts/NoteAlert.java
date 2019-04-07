package com.example.k7.koncowy.projekt.projektkoncowy.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.ICallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.NotesRepository;

import java.io.File;
import java.util.ArrayList;

public class NoteAlert {
    public static ArrayList<String> getColors(){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("#2196f3");
        arrayList.add("#f44336");
        arrayList.add("#e91e63");
        arrayList.add("#9c27b0");
        arrayList.add("#673ab7");
        arrayList.add("#009688");
        arrayList.add("#4caf50");
        arrayList.add("#cddc39");
        arrayList.add("#ff5722");
        return arrayList;
    }
    private Context context;
    private AlertDialog.Builder alert;
    private EditText noteTitle;
    private EditText noteContent;
    private NotesRepository notesRepository;
    private final File file;
    private ICallback callback;
    public NoteAlert(Context context, final File file, final ICallback callback)
    {
        this.context = context;
        this.file = file;
        this.callback = callback;
        notesRepository = new NotesRepository(context, "NotesKot.db", null, 1);
        alert = new AlertDialog.Builder(context);
        alert.setTitle("NOTATKA DO ZDJĘCIA");
        alert.setMessage("podaj tytuł i treść notatki oraz wybierz kolor tekstu");
        final View editView = getEditView();
        alert.setView(editView);
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.setPositiveButton("TAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();
                int color = noteTitle.getCurrentHintTextColor();
                String colorHex = "#"+String.format("%X", color).substring(2).toLowerCase();
                int id = getColors().indexOf(colorHex);
                Note serverNote = notesRepository.get(file.toString());
                if(serverNote==null)
                {
                    Note note = Note.Create(file.toString(), title, content, id);
                    notesRepository.addNote(note);
                }
                else
                {
                    serverNote.editNote(title,content,id);
                    notesRepository.update(serverNote);
                }
                if(callback!=null)callback.whenProcessDone();

            }
        });
    }

    public void show()
    {
        alert.show();
    }

    private View getEditView()
    {
        View editView = View.inflate(context, R.layout.note_alert, null);
        LinearLayout colors = editView.findViewById(R.id.colors);
        noteTitle = editView.findViewById(R.id.noteTitle);
        noteContent = editView.findViewById(R.id.noteContent);
        ArrayList<String> colorsHexs = getColors();
        setInputTextColors(colorsHexs.get(0));
        for (String color : colorsHexs) {
            colors.addView(getSingleColorLayout(color));
        }
        Note note = notesRepository.get(file.toString());
        if(note!=null)
        {
            noteTitle.setText(note.getTitle());
            noteContent.setText(note.getContent());
            setInputTextColors(getColors().get(note.getColor()));
        }

        return editView;
    }

    private LinearLayout getSingleColorLayout(final String color)
    {
        LinearLayout singleColorLayout = new LinearLayout(context);

        singleColorLayout.setBackgroundColor(Color.parseColor(color));
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        singleColorLayout.setLayoutParams(params);

        singleColorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInputTextColors(color);
            }
        });
        return singleColorLayout;
    }

    private void setInputTextColors(String color)
    {
        noteTitle.setTextColor(Color.parseColor(color));
        noteTitle.setHintTextColor(Color.parseColor(color));
    }
}
