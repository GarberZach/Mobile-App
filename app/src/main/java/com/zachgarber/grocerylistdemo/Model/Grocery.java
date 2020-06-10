package com.zachgarber.grocerylistdemo.Model;

public class Grocery {

    private String groceryItem;
    private String quantityNumber;
    private String dateAdded;
    private int id;


    public Grocery(String groceryItem, String quantity, String dateAdded, int id) {
        this.groceryItem = groceryItem;
        this.quantityNumber = quantity;
        this.dateAdded = dateAdded;
        this.id = id;
    }

    public Grocery(){

    }

    public String getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(String groceryItem) {
        this.groceryItem = groceryItem;
    }

    public String getQuantity() {
        return quantityNumber;
    }

    public void setQuantity(String quantity) {
        this.quantityNumber = quantity;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
