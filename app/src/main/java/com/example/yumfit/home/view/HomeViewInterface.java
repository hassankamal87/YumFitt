package com.example.yumfit.home.view;

import com.example.yumfit.pojo.Category;
import com.example.yumfit.pojo.Country;
import com.example.yumfit.pojo.Ingredient;
import com.example.yumfit.pojo.Meal;
import com.example.yumfit.pojo.MealResponse;

import java.util.List;

public interface HomeViewInterface {
    void setDailyInspirationData(List<Meal> meals);

    void onSuccessToFilter(MealResponse meals);
    void setListToCategoriesAdapter(List<Category> categories);
    void setListToCountriesAdapter(List<Country> countries);
    void onFailureResult(String message);
}
