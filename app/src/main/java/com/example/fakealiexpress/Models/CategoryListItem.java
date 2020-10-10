package com.example.fakealiexpress.Models;

public class CategoryListItem {
    private String label;
    private int imageResource;

    public CategoryListItem(String labe, int res){
        this.label = labe;
        this.imageResource = res;
    }


    public String getLabel() {
        return this.label;
    }

    public int getImageResource() {
        return this.imageResource;
    }
}
