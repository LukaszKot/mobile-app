package com.example.k7.koncowy.projekt.projektkoncowy.domain;

public class Note {
    private int _id;
    private String _imgUrl;
    private String _title;
    private String _content;
    private int _colorId;

    private Note(int id, String imgUrl, String title, String content, int colorId)
    {
        _id = id;
        _imgUrl = imgUrl;
        editNote(title,content, colorId);
    }

    public static Note Create(String imgUrl, String title, String content, int colorId)
    {
        return new Note(-1, imgUrl, title, content, colorId);
    }

    public static Note ReadFromDatbase(int id, String imgUrl, String title, String content, int colorId)
    {
        return new Note(id,imgUrl,title,content, colorId);
    }

    public void editNote(String title, String content, int colorId)
    {
        _title = title;
        _content = content;
        _colorId = colorId;
    }

    public int getId()
    {
        return _id;
    }

    public String getImgUrl() {
        return _imgUrl;
    }

    public String getTitle() {
        return _title;
    }

    public String getContent() {
        return _content;
    }

    public int getColor()
    {
        return _colorId;
    }
}
