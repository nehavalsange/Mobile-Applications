package edu.uncc.evaluation05.models;

import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseListResponse implements Serializable {

String status;
ArrayList<PurchaseList> purchase_lists = new ArrayList<>();

    public PurchaseListResponse() {
    }

    public PurchaseListResponse(String status, ArrayList<PurchaseList> purchase_lists) {
        this.status = status;
        this.purchase_lists = purchase_lists;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PurchaseList> getPurchase_lists() {
        return purchase_lists;
    }

    public void setPurchase_lists(ArrayList<PurchaseList> purchase_lists) {
        this.purchase_lists = purchase_lists;
    }

    @Override
    public String toString() {
        return "PurchaseListResponse{" +
                "status='" + status + '\'' +
                ", purchase_lists=" + purchase_lists +
                '}';
    }
}
