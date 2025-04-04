package edu.uncc.assignment14.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Bill implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String category;
    public String name;
    public Date billDate;
    public double discount;
    public double amount;

    public Bill() {
    }

    public Bill(String category, String name, Date billDate, double discount, double amount) {
        this.category = category;
        this.name = name;
        this.billDate = billDate;
        this.discount = discount;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
