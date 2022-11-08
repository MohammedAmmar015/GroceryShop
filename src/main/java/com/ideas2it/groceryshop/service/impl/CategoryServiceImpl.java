package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
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
public class
CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    private final ProductRepo productRepo;
    public CategoryServiceImpl(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    public String addCategory(CategoryRequestDto categoryRequestDto) throws Existed {
        if(categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Category Already Added");
        }
        Category category = CategoryMapper.toCategory(categoryRequestDto);
        if(categoryRequestDto.getParentId() != 0) {
            Optional<Category> category1 = categoryRepo.findById(categoryRequestDto.getParentId());
                category.setCategory(category1.get());
        }
        categoryRepo.save(category);
        return "Saved Successfully";
    }

    public List<CategoryResponseDto> getCategory() throws NotFoundException {
        List<Category> categories = categoryRepo.findByParentIdAndIsActive( true);
        if (categories == null || categories.isEmpty()) {
            throw new NotFoundException("No Products Added");
        }
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategoryDto(category));
        }
        return categories1;
    }

    public List<CategoryResponseDto> getAllSubCategory() throws NotFoundException {
        List<Category> categories = categoryRepo.findByParentIdSubCategoryAndIsActive(true);
        if (categories == null || categories.isEmpty()) {
            throw new NotFoundException("No Products Added");
        }
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategoryDto(category));
        }
        return categories1;
    }

    @Override
    public String deleteCategory(Integer id) throws NotFoundException {
        Category category = categoryRepo.findByIdAndIsActive(id, true);
        if (category == null) {
            throw new NotFoundException("Id Not Exist");
        }
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
        return "Deleted Successfully";
    }

    public String deleteSubCategory(Integer id, Integer subCategoryId) throws NotFoundException {
        Category category = categoryRepo.findSubCategoryByParentIdAndIdAndIsActive(subCategoryId, id, true);
        if (category == null) {
            throw new NotFoundException("Id Not Exist");
        }
        category.setActive(false);
        categoryRepo.save(category);
        List<Product> products = productRepo.findAllProductBySubCategoryIdAndIsActive(subCategoryId, true);
        for(Product product1: products) {
            product1.setActive(false);
            productRepo.save(product1);
        }
        return "Deleted Successfully";
    }

    @Override
    public String updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws NotFoundException, Existed {
        Category category = categoryRepo.findByIdAndParentIdAndIsActive(id, true);
        if(category == null) {
            throw new NotFoundException("Id Not Exist");
        }
        if (categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Name Exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return "Updated Successfully";
    }

    @Override
    public String updateSubCategory(Integer categoryId, Integer parentId, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepo.findByCategoyIdAndParentIdAndIsActive(categoryId, parentId, true);
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return "Updated Successfully";
    }
}
