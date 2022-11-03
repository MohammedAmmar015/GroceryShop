package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;


public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



}
