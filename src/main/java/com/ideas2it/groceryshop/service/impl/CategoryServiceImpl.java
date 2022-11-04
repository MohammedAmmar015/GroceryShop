package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public String addCategory (CategoryRequestDto categoryDto) {
        Category category = CategoryMapper.toCategory(categoryDto);
        if(categoryDto.getParentId() != 0) {
            Optional<Category> category1 = categoryRepo.findById(categoryDto.getParentId());
            category.setCategory(category1.get());
        }
        Category category2 = categoryRepo.save(category);
        return "saved";
    }

}
