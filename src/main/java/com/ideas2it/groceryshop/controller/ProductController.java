/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.ProductService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     It implements method of CRUD operations for Product.
 * </p>
 * @author Ruban
 * @since  03/11/2022
 * @version  1.0
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final Logger logger;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.logger = LogManager.getLogger(ProductController.class);
    }

    /**
     * <p>
     *     This method receive product request dto object and forward
     *     to the service layer and this method return success response dto,
     *     handles Post method API (/api/v1/products).
     * </p>
     *
     * @param productRequestDto Dto type model.
     * @return SuccessDto
     * @throws Existed exception will be thrown if the product already Exist.
     */
    @PostMapping
    public SuccessResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto)
            throws Existed, NotFound {
        logger.debug("Entered into addProduct method in product controller");
        return productService.addProduct(productRequestDto);
    }

    /**
     * <p>
     *     This method used to get products by user search
     * </p>
     * @param name contains letter or words.
     * @return list of matched products
     * @throws NotFound exception will be thrown if products not found
     *
     */
    @GetMapping("/search/{name}")
    public List<ProductResponseDto> getProductBySearch(@Valid @PathVariable("name") String name)
            throws NotFound {
        logger.debug("Entered into searchProduct method in product controller");
        return productService.getProductsBySearch(name);
    }

    /**
     * <p>
     *     This method to get products in particular category
     *     by using id and returns list of products, handles Get
     *     method API (/api/v1/products/category/{categoryId}).
     * </p>
     *
     * @param categoryId to find particular object.
     * @return Dto type object.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getProductsByCategory(@PathVariable("categoryId")
                                                              Integer categoryId) throws NotFound {
        logger.debug("Entered into getProductByCategory method in product controller");
        return productService.getProductsByCategoryId(categoryId);
    }

    /**
     * <p>
     *     This method to get products in particular sub category by
     *     its id and returns list of products, handles Get method
     *     API (/api/v1/products/category/subCategory/{subCategoryID}).
     * </p>
     *
     * @param subCategoryId to find particular object.
     * @return Dto type object.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategory(@PathVariable("subCategoryId")
                                                                 Integer subCategoryId) throws NotFound {
        logger.debug("Entered into getProductBySubCategory method in product controller");
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    /**
     * <p>
     *     This method used to get all products from data base,
     *     handles Get method API (/api/v1/products).
     * </p>
     * @return list of products.
     * @throws NotFound exception will be thrown if the products not exist.
     */
    @GetMapping
    public List<ProductResponseDto> getProducts() throws NotFound {
        logger.debug("Entered into getProducts method in product controller");
        return productService.getProducts();
    }

    /**
     * <p>
     *     This method to get products based on Location,
     *     handles Get method API (/api/v1/products/location/{locationId}).
     * </p>
     * @param locationId to fetch product from that particular location.
     * @return List of Products.
     * @throws NotFound exception will be thrown if id not exist.
     */
    @GetMapping("/location/{locationId}")
    public List<ProductResponseDto> getProductsByLocation(@PathVariable("locationId")
                                                              Integer locationId) throws NotFound {
        logger.debug("Entered into getProductByLocationId method in product controller");
        return productService.getProductsByLocation(locationId);
    }

    /**
     * <p>
     *     This Method used to get particular product by product id,
     *     handles Get method API (/api/v1/products/productId/{productID}).
     * </p>
     * @param productId to fetch particular object
     * @return Dto type product object.
     */
    @GetMapping("/productId/{productId}")
    public ProductResponseDto getProductById(@PathVariable("productId")
                                                  Integer productId) throws NotFound {
        logger.debug("Entered into getProductById method in product controller");
        return productService.getProductById(productId);
    }

    /**
     * <p>
     *     This method to delete(soft delete) product from the
     *     data base using id, handles Delete method API (/api/v1/products/{id}).
     * </p>
     *
     * @param id to find particular object.
     * @return SuccessDto
     * @throws NotFound exception will be thrown if the id is not exist.
     */
    @DeleteMapping("/{id}")
    public SuccessResponseDto deleteProductById(@PathVariable("id") Integer id) throws NotFound {
        logger.debug("Entered into deleteProduct method in product controller");
        return productService.deleteProductById(id);
    }

    /**
     * <p>
     *     This method is used to update particular product by id,
     *     handles Put method API (/api/v1/products/{id}).
     * </p>
     *
     * @param id to find the object to be updated.
     * @param productRequestDto values to get updated.
     * @return SuccessDto
     * @throws NotFound exception will be thrown if the id is not exist.
     * @throws Existed exception will be thrown if old and new fields values are same.
     */
    @PutMapping("/{id}")
    public SuccessResponseDto updateProductById(@PathVariable("id") Integer id,
                                                @RequestBody ProductRequestDto productRequestDto)
            throws NotFound, Existed {
        logger.debug("Entered into updateProduct method in product controller");
        return productService.updateProductById(id, productRequestDto);
    }
}
