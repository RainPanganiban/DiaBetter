package com.activity.diabetter;

public class FoodItem {
    private String foodName;
    private String mainIngredient;
    private String sugarLevel;
    private String category;

    public FoodItem(String foodName, String mainIngredient, String sugarLevel) {
        this.foodName = foodName;
        this.mainIngredient = mainIngredient;
        this.sugarLevel = sugarLevel;
        this.category = category;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public String getCategory() {
        return category; // Getter for category
    }
}
