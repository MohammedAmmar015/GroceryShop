package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;

public interface CategoryService {
    public String addCategory (CategoryRequestDto categoryDto);
}
