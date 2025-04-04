package edu.uncc.evaluation05.models;

import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseList implements Serializable {

    String plid;
    String name;
    ArrayList<Product> items;

    /*
    "purchase_lists": [
        {
            "plid": "9d376e3e-e2fc-4add-a118-2b108e1dc964",
            "name": "Test list 2",
            "items": [
                {
                    "pid": "cdcab5ed-f4ad-43f8-8551-c6efac39427d",
                    "quantity": 1,
                    "name": "Charlotte 49ers 11.5'' Suntime Premium Glass Face Football Helmet Wall Clock",
                    "price_per_item": 35.99,
                    "img_url": "https://www.theappsdr.com/items-imgs/charlotte-49ers-wall-clock.jpeg"
                },
                {
                    "pid": "79fa9e5f-3f44-487e-b610-a03ac9d57108",
                    "quantity": 2,
                    "name": "Charlotte 49ers 18oz. Stainless Steel Soft Touch Tumbler",
                    "price_per_item": 22.95,
                    "img_url": "https://www.theappsdr.com/items-imgs/charlotte-49ers-18oz-stainless-steel-soft-touch-tumbler.png"
                }
            ]
        },
     */


    public PurchaseList() {
    }

    public PurchaseList(String plid, String name, ArrayList<Product> items) {
        this.plid = plid;
        this.name = name;
        this.items = items;
    }

    public String getPlid() {
        return plid;
    }

    public void setPlid(String plid) {
        this.plid = plid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PurchaseList{" +
                "plid='" + plid + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}

