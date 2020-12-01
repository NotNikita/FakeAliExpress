package com.example.fakealiexpress.models;

public class Item {
    private int id;
    private String name;
    private byte[] image;
    private int price;
    private int from_categ;
    private boolean isViewed; //Была ли просмотрена вещь
    private String description = "Very good buy it please. I need to feed my 20 chinese childs. Any female born after 1993 can’t cook… all they know is mcdonald’s , charge they phone, twerk, be bisexual , eat hot chip & lie";

    public Item(){}

    public Item(int _id, String _name, byte[] _image, int _price, int _from_categ, boolean _isViewed){
        this.id = _id;
        this.image = _image;
        this.name = _name;
        this.price = _price;
        this.from_categ = _from_categ;
        this.isViewed = _isViewed;
    }

    public Item(String _name, byte[] _image, int _price, int _from_categ){
        this.image = _image;
        this.name = _name;
        this.price = _price;
        this.from_categ = _from_categ;
        this.isViewed = false;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String _name){
        this.name = _name;
    }

    public int getFrom_categ() {
        return from_categ;
    }

    public void setFrom_categ(int from_categ) {
        this.from_categ = from_categ;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] _image) {this.image = _image;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }
}
