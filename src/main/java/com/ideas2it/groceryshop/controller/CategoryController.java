package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.CategoryService;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     It implements method of CRUD operations for Category.
 * </p>
 * @author RUBAN  03/11/22
 * @version  1.0
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * <p>
     *     This Method to save incoming category Request to category Repo and
     *     it handles the post API("/api/v1/categories").
     * </p>
     * @param categoryRequestDto It receives DTO type from users.
     * @return SuccessDto.
     */
    @PostMapping
    public SuccessDto addCategory(@RequestBody CategoryRequestDto categoryRequestDto) throws Existed {
        return categoryService.addCategory(categoryRequestDto);

    }

    /**
     * <p>
     *     This method used to get all category, handles Get API("api/v1/categories)
     * </p>
     *
     * @return CategoryDto.
     * @throws NotFound will be thrown if category is empty.
     */
    @GetMapping
    public List<CategoryResponseDto> getCategory() throws NotFound {
        return categoryService.getCategory();
    }

    /**
     * <p>
     *     This method will get all the sub category,
     *     handles Get API("api/v1/categories/subCategories")
     * </p>
     *
     * @return CategoryDto
     * @throws NotFound will be thrown if sub category is empty.
     */
    @GetMapping("/subCategories")
    public List<CategoryResponseDto> getSubCategory() throws NotFound {
        return categoryService.getAllSubCategory();
    }

    /**
     * <p>
     *     This method will delete category using Id,
     *      handles the Delete API ("api/v1/categories/{id}).
     * </p>
     *
     * @param id to delete category.
     * @return SuccessDto.
     * @throws NotFound will be thrown when the id not exist.
     */
    @DeleteMapping("/{id}")
    public SuccessDto deleteCategory(@PathVariable("id") Integer id) throws NotFound {
        return categoryService.deleteCategory(id);

    }

    /**
     * <p>
     *    This method will delete category using categoryId & subCategoryId,
     *     handles the Delete API ("api/v1/categories/subCategories/{categoryId}/{subCategoryId}).
     * </p>
     *
     * @param id to delete Sub category.
     * @param subCategoryId to delete sub category.
     * @return SuccessDto
     * @throws NotFound will be thrown if the id not exist.
     */
    @DeleteMapping("/subCategories/{categoryId}/{subCategoryId}")
    public SuccessDto deleteSubCategory(@PathVariable("categoryId") Integer id, @PathVariable("subCategoryId") Integer subCategoryId) throws NotFound {
        return categoryService.deleteSubCategory(id, subCategoryId);
    }

    /**
     * <p>
     *     This method used to update category Details.
     * </p>
     *
     * @param id to find which object to update
     * @param categoryRequestDto values to be update.
     * @return SuccessDto
     * @throws Existed will be thrown if new values are same as old.
     * @throws NotFound will be thrown if the id not exist.
     */
    @PutMapping("/{id}/")
    public SuccessDto updateCategory(@PathVariable("id") Integer id, @RequestBody CategoryRequestDto categoryRequestDto) throws Existed, NotFound {
        return categoryService.updateCategory(id, categoryRequestDto);
    }

    /**
     * <p>
     *     This method to update sub category fields.
     * </p>
     *
     * @param categoryId to update.
     * @param parentId to update.
     * @param categoryRequestDto values to be Update.
     * @return SuccessDto
     * @throws Existed will be thrown if new values are same as old.
     * @throws NotFound will be thrown if the id is not exist.
     */
    @PutMapping("/subCategories/{categoryId}/{parentId}")
    public SuccessDto updateSubCategory(@PathVariable("categoryId") Integer categoryId, @PathVariable("parentId") Integer parentId, @RequestBody CategoryRequestDto categoryRequestDto) throws Existed, NotFound {
        return categoryService.updateSubCategory(categoryId, parentId, categoryRequestDto);
    }
}
