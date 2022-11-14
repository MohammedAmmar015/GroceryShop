package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author  RUBAN
 * @version   1.0
 * @since 03/11/2022
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CategoryServiceTest.class})
public class CategoryServiceTest {

    @Mock
    CategoryRepo categoryRepo;

    @Test
    @Order(3)
    public void test_getCategory() throws NotFound {

        List<Category> categories = new ArrayList<>();
        Category category = new Category(1, "Fruits & vegetables", null, true);
        categories.add(new Category(1,"Fruits", category, true));
       /* List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        for(Category category : categories) {
             categoryResponseDtos.add(CategoryMapper.toCategoryDto(category));
        }*/

        when(categoryRepo.findByParentIdAndIsActive( true)).thenReturn(categories);
        assertEquals(1, categoryRepo.findByParentIdAndIsActive(true).size());
    }

    @Test
    @Order(4)
    public void test_getSubCategory() throws NotFound {
        List<Category> categories = new ArrayList<>();
        Category category = new Category(1, "fruits & vegetables", null,true);
        categories.add(new Category(2,"vegetables", category, true));

        when(categoryRepo.findByParentIdSubCategoryAndIsActive(true)).thenReturn(categories);
        assertEquals("vegetables", categoryRepo.findByParentIdSubCategoryAndIsActive(true).get(0).getName());
    }

    @Test
    @Order(1)
    public void test_addCategory() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Biscuits", 0);
        Category category = CategoryMapper.toCategory(categoryRequestDto);

        when(categoryRepo.save(category)).thenReturn(category);
        assertEquals("Biscuits", categoryRepo.save(category).getName());
    }

    @Test
    @Order(2)
    public void test_addSubCategory() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("vegetables",  1);
        Category category = CategoryMapper.toCategory(categoryRequestDto);

        when(categoryRepo.save(category)).thenReturn(category);
        assertEquals("vegetables", categoryRepo.save(category).getName());
    }

    @Test
    @Order(5)
    public void test_updateCategory() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("Soap & Shampoo", 0);
        Category category = new Category(1, "Biscuits", null, true);
        category.setName(categoryRequestDto.getName());

        when(categoryRepo.save(category)).thenReturn(category);
        assertEquals("Soap & Shampoo",categoryRepo.save(category).getName());
    }

    @Test
    @Order(6)
    public void test_deleteCategory() {
        Category category = new Category(1, "Fruits & vegetables", null, true);
        category.setActive(false);

        when(categoryRepo.save(category)).thenReturn(category);
        assertEquals(false, categoryRepo.save(category).isActive());
    }
}

