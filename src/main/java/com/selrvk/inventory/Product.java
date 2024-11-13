package com.selrvk.inventory;

public class Product {

    private final int id;
    private final byte[] img;
    private final String name;
    private final int stock;
    private final String brand;
    private final String shelfLocation;

    public Product(int id, byte[] img, String name, int stock, String brand, String shelfLocation){

        this.id = id;
        this.img = img;
        this.name = name;
        this.stock = stock;
        this.brand = brand;
        this.shelfLocation = shelfLocation;
    }

    public Product(byte[] img,String name, int stock, String brand, String shelfLocation){

        this.id = 0;
        this.img = img;
        this.name = name;
        this.stock = stock;
        this.brand = brand;
        this.shelfLocation = shelfLocation;
    }

    public int getId(){ return  id; }
    public byte[] getImg(){ return img; }
    public String getName(){ return name; }
    public int getStock(){ return stock; }
    public String getBrand(){ return brand; }
    public String getLocation(){ return shelfLocation; }

}
