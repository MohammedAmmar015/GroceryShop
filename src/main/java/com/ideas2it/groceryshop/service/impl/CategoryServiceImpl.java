package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

   /* public Optional<Category> getCategoryById(Integer id) {
        Optional<Category> category = categoryRepo.findById(id);
       // return CategoryMapper.toCategoryDto(category.get());
        return category;
    }*/

    public List<CategoryResponseDto> getCategory() {
        List<Category> categories = categoryRepo.findByNotNull();
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategory(category));
        }
        return categories1;
    }

    public List<CategoryResponseDto> getAllSubCategory() {
        List<Category> categories = categoryRepo.findByParentId();
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategory(category));
        }
        return categories1;
    }

}
