package com.example.k7.koncowy.projekt.projektkoncowy.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.k7.koncowy.projekt.projektkoncowy.domain.Note;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class NotesRepository extends SQLiteOpenHelper {
    public NotesRepository(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'img_url' TEXT, 'title' TEXT, 'content' TEXT, 'color' colorId)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public boolean addNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("img_url", note.getImgUrl());
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("color", note.getColor());

        try
        {
            db.insertOrThrow("notes", null, contentValues);
        }
        catch (Exception ex)
        {
            db.close();
            return false;
        }

        db.close();
        return true;
    }

    public Note get(String url)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM notes WHERE img_url='"+url+"'" , null);
        while(result.moveToNext()){
            notes.add(Note.ReadFromDatbase(result.getInt(result.getColumnIndex("_id")),
                    result.getString(result.getColumnIndex("img_url")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("content")),
                    result.getInt(result.getColumnIndex("color"))
            ));
        }
        Note theNote;
        try
        {
            theNote = notes.get(0);
        }catch (IndexOutOfBoundsException e)
        {
            db.close();
            return null;
        }
        db.close();
        return theNote;

    }

    public void update(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("content", note.getContent());
        contentValues.put("color", note.getColor());

        db.update("notes", contentValues, "_id= ? "
                , new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void delete(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "_id = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public ArrayList<Note> getAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM notes" , null);
        while(result.moveToNext()){
            notes.add(Note.ReadFromDatbase(result.getInt(result.getColumnIndex("_id")),
                    result.getString(result.getColumnIndex("img_url")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("content")),
                    result.getInt(result.getColumnIndex("color"))
            ));
        }
        return notes;
    }
}
