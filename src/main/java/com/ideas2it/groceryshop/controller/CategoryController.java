package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 *     It implements method of CRUD operations for Category.
 * </p>
 * @author RUBAN  04/11/22
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

    /**
     * <p>
     *     This Method to create and insert category.
     * </p>
     * @param categoryRequestDto It receives DTO type model from users.
     * @return returns message.
     */
    @PostMapping("/")
    public String insertCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws Existed {
        return categoryService.addCategory(categoryRequestDto);

    }

    @GetMapping("/")
    public List<CategoryResponseDto> getCategory() throws NotFoundException {
        return categoryService.getCategory();
    }

    @GetMapping("/subCategories")
    public List<CategoryResponseDto> getSubCategory() throws NotFoundException {
        return categoryService.getAllSubCategory();
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) throws NotFoundException {
        return categoryService.deleteCategory(id);

    }

    @DeleteMapping("/subCategories/{categoryId}/{subCategoryId}")
    public String deleteSubCategory(@PathVariable("categoryId") Integer id, @PathVariable("subCategoryId") Integer subCategoryId) throws NotFoundException {
        return categoryService.deleteSubCategory(id, subCategoryId);
    }

    @PutMapping("/{id}/")
    public String updateCategory(@PathVariable("id") Integer id, @RequestBody CategoryRequestDto categoryRequestDto) throws Existed, NotFoundException {
        return categoryService.updateCategory(id, categoryRequestDto);
    }

    @PutMapping("/subCategories/{categoryId}/{parentId}")
    public String updateSubCategory(@PathVariable("categoryId") Integer categoryId, @PathVariable("parentId") Integer parentId, @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.updateSubCategory(categoryId, parentId, categoryRequestDto);
    }
}
