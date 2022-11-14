package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

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
@NoArgsConstructor
public class
CategoryServiceImpl implements CategoryService {
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
    public SuccessDto addCategory(CategoryRequestDto categoryRequestDto) throws Existed {
        if(categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Category Already Added");
        }
        Category category = CategoryMapper.toCategory(categoryRequestDto);
        if(categoryRequestDto.getParentId() != 0) {
            Optional<Category> category1 = categoryRepo.findById(categoryRequestDto.getParentId());
            category.setCategory(category1.get());
        }
        categoryRepo.save(category);
        return new SuccessDto(201, "Category Added Successfully");
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
        List<Category> categories = categoryRepo.findByParentIdAndIsActive( true);
        if (categories == null || categories.isEmpty()) {
            throw new NotFound("Category Not Added");
        }
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategoryDto(category));
        }
        return categories1;
    }

    /**
     * <p>
     *     This method will get all sub categories which are in exist.
     * </p>
     *
     * @return sub category list if exist otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if no sub category exists.
     */
    public List<CategoryResponseDto> getAllSubCategory() throws NotFound {
        List<Category> categories = categoryRepo.findByParentIdSubCategoryAndIsActive(true);
        if (categories == null || categories.isEmpty()) {
            throw new NotFound("SubCategory Not Added");
        }
        List<CategoryResponseDto> categories1 = new ArrayList<>();
        for(Category category:categories) {
            categories1.add(CategoryMapper.toCategoryDto(category));
        }
        return categories1;
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
    public SuccessDto deleteCategory(Integer id) throws NotFound {
        Category category = categoryRepo.findByIdAndIsActive(id, true);
        if (category == null) {
            throw new NotFound("Category Id Not Found");
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
        return new SuccessDto(200, "Deleted Successfully");
    }

    /**
     * <p>
     *     This method will delete sub category using id.
     * </p>
     *
     * @param id to find which object to get deleted.
     * @param subCategoryId to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if sub category doesn't exist.
     */
    public SuccessDto deleteSubCategory(Integer id, Integer subCategoryId) throws NotFound {
        Category category = categoryRepo.findSubCategoryByParentIdAndIdAndIsActive(subCategoryId, id, true);
        if (category == null) {
            throw new NotFound("Subcategory Id Not Found");
        }
        category.setActive(false);
        categoryRepo.save(category);
        List<Product> products = productRepo.findAllProductBySubCategoryIdAndIsActive(subCategoryId, true);
        for(Product product1: products) {
            product1.setActive(false);
            productRepo.save(product1);
        }
        return new SuccessDto(200, "Deleted Successfully");
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
    public SuccessDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws NotFound, Existed {
        Category category = categoryRepo.findByIdAndParentIdAndIsActive(id, true);
        if(category == null) {
            throw new NotFound("Category Id Not Found");
        }
        if (categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Category Name Exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return new SuccessDto(200, "Category Details Updated Successfully");
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
    public SuccessDto updateSubCategory(Integer categoryId, Integer parentId, CategoryRequestDto categoryRequestDto) throws NotFound, Existed {
        Category category = categoryRepo.findByCategoryIdAndParentIdAndIsActive(categoryId, parentId, true);
        if(category == null) {
            throw new NotFound("Sub Category Id Not Found");
        }
        if (categoryRepo.existsByName(categoryRequestDto.getName())) {
            throw new Existed("Sub Category Name Exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepo.save(category);
        return new SuccessDto(200, "Sub Category Details Updated Successfully");
    }
}
