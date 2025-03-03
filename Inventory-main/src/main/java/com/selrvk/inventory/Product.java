package com.selrvk.inventory;

public class Product {

    private final int id;
    private byte[] img;
    private String name;
    private int stock;
    private int srp;
    private int buyingPrice;
    private String manufacturer;

    public Product(int id, byte[] img, String name, int stock, int srp, int buyingPrice, String manufacturer){

        this.id = id;
        this.img = img;
        this.name = name;
        this.stock = stock;
        this.srp = srp;
        this.buyingPrice = buyingPrice;
        this.manufacturer = manufacturer;
    }

    public Product(byte[] img,String name, int stock, int srp, int buyingPrice, String manufacturer){

        this.id = 0;
        this.img = img;
        this.name = name;
        this.stock = stock;
        this.srp = srp;
        this.buyingPrice = buyingPrice;
        this.manufacturer = manufacturer;
    }

    public int getId(){ return  id; }
    public byte[] getImg(){ return img; }
    public String getName(){ return name; }
    public int getStock(){ return stock; }
    public int getSrp(){ return srp; }
    public int getBuyingPrice(){ return buyingPrice; }
    public String getManufacturer(){ return manufacturer; }
    public void setName(String name){ this.name = name; }
    public void setImg(byte[] img){ this.img = img; }
    public void setStock(int stock){ this.stock = stock; }
    public void setSrp(int srp){ this.srp = srp; }
    public void setBuyingPrice(int buyingPrice){ this.buyingPrice = buyingPrice; }
    public void setManufacturer(String manufacturer){ this.manufacturer = manufacturer; }

}
