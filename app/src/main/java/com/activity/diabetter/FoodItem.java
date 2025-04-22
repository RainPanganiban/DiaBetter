package com.activity.diabetter;

public class FoodItem {
    private String foodName;
    private String mainIngredient;
    private String sugarLevel;

    public FoodItem(String foodName, String mainIngredient, String sugarLevel) {
        this.foodName = foodName;
        this.mainIngredient = mainIngredient;
        this.sugarLevel = sugarLevel;
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
}
