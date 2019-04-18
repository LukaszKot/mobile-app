package com.example.k7.koncowy.projekt.projektkoncowy.domain;

import java.io.Serializable;

public class TextInfo implements Serializable {
    private String fontName;
    private String text;
    private int mainColor;
    private int edgesColor;
    private float width;
    private float height;

    public TextInfo(String fontName, String text, int mainColor, int edgesColor,
                    int width, int height)
    {
        this.fontName = fontName;
        this.text= text;
        this.mainColor = mainColor;
        this.edgesColor = edgesColor;
        this.width =width;
        this.height = height;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
