package com.selrvk.inventory;

import java.sql.Date;
import java.util.List;

public class Orders {

    private int order_id;
    private Date date;
    private String customer_name;
    private List<OrdersProducts> productList;

    public Orders(int order_id, Date date, String customer_name){

        this.order_id = order_id;
        this.date = date;
        this.customer_name = customer_name;
    }

    public Orders(Date date, String customer_name, List<OrdersProducts> productList){

        this.productList = productList;
        this.date = date;
        this.customer_name = customer_name;
    }

    public String getCustomer_name() {return customer_name;}

    public void setCustomer_name(String customer_name) {this.customer_name = customer_name;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public int getOrder_id() {return order_id;}

    public void setOrder_id(int order_id) {this.order_id = order_id;}

    public List<OrdersProducts> getProductList() {return productList;}

    public void setProductList(List<OrdersProducts> productList) {this.productList = productList;}
}
