package edu.uncc.evaluation05.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductResponse implements Serializable {
    String status;
    ArrayList<Product> products = new ArrayList<>();

    public ProductResponse() {
    }

    public ProductResponse(String status, ArrayList<Product> products) {
        this.status = status;
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "status='" + status + '\'' +
                ", products=" + products +
                '}';
    }
}
