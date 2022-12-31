package com.example.thenewsapp;

import com.example.thenewsapp.objects.enums.Categories;

import java.io.Serializable;

public class Item implements Serializable {
    private String id;
    private String tieuDe;
    private String tacGia;
    private String content;
    private Categories category;
    private String imgUri="";
    private String filename="";

    public Item() {
    }

    public Item(String id, String tieuDe, String tacGia, String content, Categories category, String imgUri, String filename) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.content = content;
        this.category = category;
        this.imgUri = imgUri;
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", tieuDe='" + tieuDe + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                ", imgUri='" + imgUri + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
