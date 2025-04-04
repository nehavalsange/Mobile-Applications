package edu.uncc.evaluation05.models;

import java.io.Serializable;

public class ProductList implements Serializable {
    String pid;
    String name;
    String img_url;
    double price_per_item;
    /*
     "pid": "cdcab5ed-f4ad-43f8-8551-c6efac39427d",
            "name": "Charlotte 49ers 11.5'' Suntime Premium Glass Face Football Helmet Wall Clock",
            "img_url": "https://www.theappsdr.com/items-imgs/charlotte-49ers-wall-clock.jpeg",
            "price_per_item": 35.99
     */

    public ProductList() {
    }

    public ProductList(String pid, String name, String img_url, double price_per_item) {
        this.pid = pid;
        this.name = name;
        this.img_url = img_url;
        this.price_per_item = price_per_item;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice_per_item() {
        return price_per_item;
    }

    public void setPrice_per_item(double price_per_item) {
        this.price_per_item = price_per_item;
    }

    @Override
    public String toString() {
        return "PurchaseList{" +
                "pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", price_per_item=" + price_per_item +
                '}';
    }
}
