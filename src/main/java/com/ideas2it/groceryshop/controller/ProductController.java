package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.ProductService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *     It implements method of CRUD operations for Product.
 * </p>
 * @author RUBAN  04/11/22
 * @version  1.0
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * <p>
     *     This method to add product.
     * </p>
     *
     * @param productRequestDto Dto type model.
     * @return SuccessDto
     * @throws Existed will be thrown if the product already Exist.
     */
    @PostMapping("/")
    public SuccessDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws Existed {
        return productService.addProduct(productRequestDto);

    }

    /**
     * <p>
     *     This method to get product in category
     * </p>
     *
     * @param categoryId to find particular object.
     * @return Dto type model.
     * @throws NotFound will be thrown if the id not exist.
     */
    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) throws NotFound {
        return productService.getProductsByCategoryId(categoryId);
    }

    /**
     * <p>
     *     This method to get Products in sub category.
     * </p>
     *
     * @param subCategoryId to find the object to retrieve.
     * @return Dto type model.
     * @throws NotFound will be thrown if the id not exist.
     */
    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategoryId(@PathVariable("subCategoryId") Integer subCategoryId) throws NotFound {
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    /**
     * <p>
     *     This method used to get all products from data base.
     * </p>
     *
     * @return products.
     * @throws NotFound will be thrown if the products not exist.
     */
    @GetMapping
    public List<ProductResponseDto> getProducts() throws NotFound {
        return productService.getProducts();
    }

    /**
     * <p>
     *     This method used to get particular product by Id.
     * </p>
     *
     * @param id to find particular object.
     * @return product.
     * @throws NotFound will be thrown if the id not exist.
     */
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Integer id) throws NotFound {
        return productService.getProductById(id);
    }

    /**
     * <p>
     *     This method to delete product by id.
     * </p>
     *
     * @param id to find particular object.
     * @return SuccessDto
     * @throws NotFound will be thrown if the id is not exist.
     */
    @DeleteMapping("/{id}")
    public SuccessDto deleteProductById(@PathVariable("id") Integer id) throws NotFound {
        return productService.deleteProductById(id);
    }

    /**
     * <p>
     *     This method is used to update particular product by id.
     * </p>
     *
     * @param id to find the object to be updated.
     * @param productRequestDto values to get updated.
     * @return SuccessDto
     * @throws NotFound will be thrown if the id is not exist.
     * @throws Existed will be thrown if old and new fields values are same.
     */
    @PutMapping("/{id}")
    public SuccessDto updateProductById(@PathVariable("id") Integer id, @RequestBody ProductRequestDto productRequestDto) throws NotFound, Existed {
        return productService.updateProductById(id, productRequestDto);
    }
}
