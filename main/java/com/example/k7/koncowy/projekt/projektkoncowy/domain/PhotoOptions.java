package com.example.k7.koncowy.projekt.projektkoncowy.domain;

public class PhotoOptions {
    private String imgSrc;

    private String name;

    public PhotoOptions(String imgSrc, String name)
    {
        this.imgSrc = imgSrc;
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getName() {
        return name;
    }

}
