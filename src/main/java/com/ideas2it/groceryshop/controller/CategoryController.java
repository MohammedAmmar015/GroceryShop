package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     It controls all the category APIs
 * </p>
 * @author   RUBAN
 * @version  1.0
 *
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public String insertCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.addCategory(categoryRequestDto);

    }

   /* @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable("id") Integer id) {
       return categoryService.getCategoryById(id);
    }*/

    @GetMapping("/")
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping("/subCategories")
    public List<CategoryResponseDto> getSubCategory() {
        return categoryService.getAllSubCategory();
    }
}
