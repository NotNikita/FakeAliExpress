package com.example.fakealiexpress.models;

public class Category {
    private int id;
    private String label;
    private byte[] imageResource;

    public Category(int _id, String labe, byte[] res){
        this.id = _id;
        this.label = labe;
        this.imageResource = res;
    }

    public Category(String labe, byte[] res){
        this.label = labe;
        this.imageResource = res;
    }


    public String getLabel() {
        return this.label;
    }

    public byte[] getImageResource() {
        return this.imageResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
