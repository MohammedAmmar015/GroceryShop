package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.CategoryService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This class implement method for crud operations for Category.
 * </p>
 *
 * @author RUBAN  03/11/2022
 * @version  1.0
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepo categoryRepo;

    private ProductRepo productRepo;

    /**
     * <p>
     *     This method to add category to data base.
     * </p>
     *
     * @param categoryRequestDto dto type object.
     * @return SuccessDto
     * @throws Existed will be thrown if category already Exists.
     */
    public SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto) throws Existed {
        if(categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Category already added");
        }
        Category categories = CategoryMapper.toCategory(categoryRequestDto);
        if(categoryRequestDto.getParentId() != 0) {
            Optional<Category> category = categoryRepo.findById(categoryRequestDto.getParentId());
            category.ifPresent(categories::setCategory);
        }
        categoryRepo.save(categories);
        return new SuccessResponseDto(201, "Category added successfully");
    }

    /**
     * <p>
     *     This method to retrieve all category list.
     * </p>
     *
     * @return Category List.
     * @throws NotFound will be thrown if no category is added.
     */
    public List<CategoryResponseDto> getCategory() throws NotFound {
        List<Category> resultCategories = categoryRepo.findByParentIdAndIsActive( true);
        if (resultCategories == null || resultCategories.isEmpty()) {
            throw new NotFound("Category not added");
        }
        List<CategoryResponseDto> categoriesList = new ArrayList<>();
        for(Category category:resultCategories) {
            categoriesList.add(CategoryMapper.toCategoryDto(category));
        }
        return categoriesList;
    }

    /**
     * <p>
     *     This method will get all sub categories which are in exist.
     * </p>
     *
     * @return sub category list if exist otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if no sub category exists.
     */
    public List<SubCategoryResponseDto> getAllSubCategory() throws NotFound {
        List<Category> categories = categoryRepo.findByParentIdSubCategoryAndIsActive(true);
        if (categories == null || categories.isEmpty()) {
            throw new NotFound("Subcategory not added");
        }
        List<SubCategoryResponseDto> categoriesList = new ArrayList<>();
        for(Category category:categories) {
            categoriesList.add(CategoryMapper.toSubCategoryDto(category));
        }
        return categoriesList;
    }

    /**
     * <p>
     *     This method will delete category using id.
     * </p>
     *
     * @param id to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if category doesn't exist.
     */
    @Override
    public SuccessResponseDto deleteCategory(Integer id) throws NotFound {
        Category categories = categoryRepo.findByIdAndIsActive(id, true);
        if (categories == null) {
            throw new NotFound("Category id not found");
        }
        categories.setActive(false);
        List<Category> subCategories = categoryRepo.findSubCategoryByParentId(id);
        for (Category category: subCategories) {
            category.setActive(false);
            categoryRepo.save(category);
        }
        List<Product> products = productRepo.findAllProductByCategory(id);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
        categoryRepo.save(categories);
        return new SuccessResponseDto(200, "category deleted Successfully");
    }

    /**
     * <p>
     *     This method will delete sub category using id.
     * </p>
     *
     * @param parentId to find which object to get deleted.
     * @param categoryId to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if sub category doesn't exist.
     */
    public SuccessResponseDto deleteSubCategory(Integer parentId, Integer categoryId) throws NotFound {
        Category category = categoryRepo.findSubCategoryByParentIdAndCategoryIdAndIsActive(parentId, categoryId, true);
        if (category == null) {
            throw new NotFound("Subcategory id not found");
        }
        category.setActive(false);
        categoryRepo.save(category);
        List<Product> products = productRepo.findAllProductBySubCategoryId(categoryId);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
        return new SuccessResponseDto(200, "Subcategory deleted successfully");
    }

    /**
     * <p>
     *     This method used to update Category.
     * </p>
     *
     * @param id to find which object to update.
     * @param categoryRequestDto contains values to get updated.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if id doesn't match.
     * @throws Existed exception will be thrown if values are already exist.
     */
    @Override
    public SuccessResponseDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws NotFound, Existed {
        Category category = categoryRepo.findByIdAndParentIdAndIsActive(id, true);
        if(category == null) {
            throw new NotFound("Category id not found");
        }
        if (categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Category name exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return new SuccessResponseDto(200, "Category details updated successfully");
    }

    /**
     * <p>
     *     This method used to update Sub Category.
     * </p>
     *
     * @param categoryId to find object to get update.
     * @param parentId to find object to get update.
     * @param categoryRequestDto contains values to get updated.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if id doesn't match.
     * @throws Existed exception will be thrown if values are already exist.
     */
    @Override
    public SuccessResponseDto updateSubCategory(Integer categoryId, Integer parentId, CategoryRequestDto categoryRequestDto) throws NotFound, Existed {
        Category category = categoryRepo.findByCategoryIdAndParentIdAndIsActive(categoryId, parentId, true);
        if(category == null) {
            throw new NotFound("Subcategory id not found");
        }
        if (categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Subcategory name exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return new SuccessResponseDto(200, "Subcategory details updated successfully");
    }
}
