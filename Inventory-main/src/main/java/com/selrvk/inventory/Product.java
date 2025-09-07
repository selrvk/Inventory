package com.selrvk.inventory;

public class Product {

    private final int id;
    //private byte[] img;
    private String name;
    private int stock;
    private double srp;
    private double buyingPrice;
    private String manufacturer;

    public Product(int id, String name, int stock, double srp, double buyingPrice, String manufacturer){

        this.id = id;
        //this.img = img;
        this.name = name;
        this.stock = stock;
        this.srp = srp;
        this.buyingPrice = buyingPrice;
        this.manufacturer = manufacturer;
    }

    public Product(String name, int stock, double srp, double buyingPrice, String manufacturer){

        this.id = 0;
        //this.img = img;
        this.name = name;
        this.stock = stock;
        this.srp = srp;
        this.buyingPrice = buyingPrice;
        this.manufacturer = manufacturer;
    }


    public int getId(){ return  id; }
    //public byte[] getImg(){ return img; }
    public String getName(){ return name; }
    public int getStock(){ return stock; }
    public double getSrp(){ return srp; }
    public double getBuyingPrice(){ return buyingPrice; }
    public String getManufacturer(){ return manufacturer; }
    public void setName(String name){ this.name = name; }
    //public void setImg(byte[] img){ this.img = img; }
    public void setStock(int stock){ this.stock = stock; }
    public void setSrp(double srp){ this.srp = srp; }
    public void setBuyingPrice(double buyingPrice){ this.buyingPrice = buyingPrice; }
    public void setManufacturer(String manufacturer){ this.manufacturer = manufacturer; }

}
