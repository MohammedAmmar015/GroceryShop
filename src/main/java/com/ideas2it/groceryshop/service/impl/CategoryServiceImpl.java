package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    private final ProductRepo productRepo;
    public CategoryServiceImpl(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
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

    public List<CategoryResponseDto> getCategory() {
        List<Category> categories = categoryRepo.findByParentIdAndIsActive( true);
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategory(category));
        }
        return categories1;
    }

    public List<CategoryResponseDto> getAllSubCategory() {
        List<Category> categories = categoryRepo.findByParentIdSubCategoryAndIsActive(true);
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategory(category));
        }
        return categories1;
    }

    @Override
    public String deleteCategory(Integer id) {
        Category category = categoryRepo.findByIdAndIsActive(id, true);
        category.setActive(false);
        List<Category> categories = categoryRepo.findSubCategoryByParentId(id);
        for (Category category1: categories) {
             category1.setActive(false);
            categoryRepo.save(category1);
        }
        List<Product> products = productRepo.findAllProductByCategoryIdAndIsActive(id, true);
        for(Product product1: products) {
            product1.setActive(false);
            productRepo.save(product1);
        }
        categoryRepo.save(category);
        return "deleted";
    }

    public String deleteSubCategory(Integer id, Integer subCategoryId) {
        Category category = categoryRepo.findCategoryByParentIdAndId(subCategoryId, id);
        category.setActive(false);
        categoryRepo.save(category);

        List<Product> products = productRepo.findAllProductBySubCategoryIdAndIsActive(subCategoryId, true);
        for(Product product1: products) {
            product1.setActive(false);
            productRepo.save(product1);
        }
        return "deleted";
    }

//    @Override
//    public String deleteProduct(Integer id) {
//        categoryRepo.findById(id);
//    }

    @Override
    public String updateCategory(Integer id, String categoryName) {
        Category category = categoryRepo.findByIdAndIsActive(id, true);
        category.setName(categoryName);
        categoryRepo.save(category);
        return "category name Updated";
    }

}
