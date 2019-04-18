package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import java.io.Serializable;

public class TextInfo implements Serializable {
    private String fontName;
    private String text;
    private int mainColor;
    private int edgesColor;

    public TextInfo(String fontName, String text, int mainColor, int edgesColor)
    {
        this.fontName = fontName;
        this.text= text;
        this.mainColor = mainColor;
        this.edgesColor = edgesColor;
    }

    public int getEdgesColor() {
        return edgesColor;
    }

    public void setEdgesColor(int edgesColor) {
        this.edgesColor = edgesColor;
    }

    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
}
