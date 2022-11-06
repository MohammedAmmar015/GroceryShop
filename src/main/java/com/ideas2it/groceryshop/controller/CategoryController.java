package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     It controls all the category APIs
 * </p>
 * @author RUBAN
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

    @GetMapping("/")
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping("/subCategories")
    public List<CategoryResponseDto> getSubCategory() {
        return categoryService.getAllSubCategory();
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Integer id){
        return categoryService.deleteCategory(id);

    }

    @DeleteMapping("/subCategories/{categoryId}/{subCategoryId}")
    public String deleteSubCategory(@PathVariable("categoryId") Integer id, @PathVariable("subCategoryId") Integer subCategoryId) {
        return categoryService.deleteSubCategory(id, subCategoryId);
    }

//    @DeleteMapping("/subCategories/products/{id}")
//    public String deleteProduct(@PathVariable("id") Integer id) {
//        return categoryService.deleteProduct(id);
//    }

    @PutMapping("/{id}/{categoryName}")
    public String updateCategory(@PathVariable("id") Integer id, @PathVariable("categoryName") String categoryName) {
        return categoryService.updateCategory(id, categoryName);
    }
}
