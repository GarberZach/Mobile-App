package com.zachgarber.grocerylistdemo.Model;

public class Meals {
    private String meal;
    private String ingredients;
    private String cookingNote;
    public int ID;



    public Meals(String meal, String ingredients, String cookingNote, int ID) {
        this.cookingNote=cookingNote;
        this.meal=meal;
        this.ingredients=ingredients;
        this.ID=ID;

    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingNote() {
        return cookingNote;
    }

    public void setCookingNote(String cookingNote) {
        this.cookingNote = cookingNote;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
