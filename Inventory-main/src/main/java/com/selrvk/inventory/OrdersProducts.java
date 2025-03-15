package com.selrvk.inventory;

public class OrdersProducts {

    private int order_id;
    private int product_id;
    private String product_name;
    private int order_quantity;
    private int product_price;

    public OrdersProducts(int order_id, String product_name, int product_id, int order_quantity, int product_price){
        this.order_id = order_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.order_quantity = order_quantity;
        this.product_price = product_price;
    }

    public OrdersProducts(String product_name, int product_id, int order_quantity, int product_price){
        this.product_name = product_name;
        this.product_id = product_id;
        this.order_quantity = order_quantity;
        this.product_price = product_price;
    }

    public OrdersProducts(String product_name, int order_quantity, int product_price){
        this.product_name = product_name;
        this.order_quantity = order_quantity;
        this.product_price = product_price;
    }

    public int getProduct_id() {return this.product_id;}
    public int getProduct_price() {return product_price;}

    public void setProduct_price(int product_price) {this.product_price = product_price;}

    public int getOrder_quantity() {return order_quantity;}

    public void setOrder_quantity(int order_quantity) {this.order_quantity = order_quantity;}

    public String getProduct_name() {return product_name;}

    public void setProduct_name(String product_name) {this.product_name = product_name;}

    public int getOrder_id() {return order_id;}

    public void setOrder_id(int order_id) {this.order_id = order_id;}
}
