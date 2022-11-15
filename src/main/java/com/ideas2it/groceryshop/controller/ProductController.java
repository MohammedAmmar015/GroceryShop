package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.ProductService;
import lombok.AllArgsConstructor;
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
 * 03/11/2022
 * @version  1.0
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    /**
     * <p>
     *     This method to add product.
     * </p>
     *
     * @param productRequestDto Dto type model.
     * @return SuccessDto
     * @throws Existed exception will be thrown if the product already Exist.
     */
    @PostMapping
    public SuccessResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) throws Existed, NotFound {
        return productService.addProduct(productRequestDto);

    }

    /**
     * <p>
     *     This method to get product in category based on Location.
     * </p>
     *
     * @param categoryId to find particular object.
     * @return Dto type object.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/{locationId}/{categoryId}")
    public List<ProductResponseDto> getProductsByLocationIdAndCategoryId(@PathVariable("locationId") Integer locationId, @PathVariable("categoryId") Integer categoryId) throws NotFound {
        return productService.getProductsByLocationIdAndCategoryId(locationId, categoryId);
    }

    /**
     * <p>
     *     This method to get products  in particular category.
     * </p>
     *
     * @param categoryId to find particular object.
     * @return Dto type object.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) throws NotFound {
        return productService.getProductsByCategoryId(categoryId);
    }

    /**
     * <p>
     *     This method to get products  in particular sub category.
     * </p>
     *
     * @param subCategoryId to find particular object.
     * @return Dto type object.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategoryId(@PathVariable("subCategoryId") Integer subCategoryId) throws NotFound {
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    /**
     * <p>
     *     This method to get Products in sub category based on Location.
     * </p>
     *
     * @param subCategoryId to find the object to retrieve.
     * @return Dto type model.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @GetMapping("/category/subCategory/{locationId}/{subCategoryId}")
    public List<ProductResponseDto> getProductsByLocationIdAndSubCategoryId(@PathVariable("locationId") Integer locationId, @PathVariable("subCategoryId") Integer subCategoryId) throws NotFound {
        return productService.getProductsByLocationIdAndSubCategoryId(locationId, subCategoryId);
    }

    /**
     * <p>
     *     This method used to get all products from data base.
     * </p>
     * @return products.
     * @throws NotFound exception will be thrown if the products not exist.
     */
    @GetMapping
    public List<ProductResponseDto> getProducts() throws NotFound {
        return productService.getProducts();
    }

    /**
     * <p>
     *     This method to get products based on Location.
     * </p>
     * @param locationId to fetch product from that particular location.
     * @return List of Products.
     * @throws NotFound exception will be thrown if id not exist.
     */
    @GetMapping("/location/{locationId}")
    public List<ProductResponseDto> getProductsByLocation(@PathVariable("locationId") Integer locationId) throws NotFound {
        return productService.getProductsByLocation(locationId);
    }

    /**
     * <p>
     *     This Method used to get particular product object by product id.
     * </p>
     * @param productId to fetch particular object
     * @return Dto type product object.
     */
    @GetMapping("/productId/{productId}")
    public ProductResponseDto getProductById(@PathVariable("productId") Integer productId) throws NotFound {
        return productService.getProductById(productId);
    }

    /**
     * <p>
     *     This method to delete product by id.
     * </p>
     *
     * @param id to find particular object.
     * @return SuccessDto
     * @throws NotFound exception will be thrown if the id is not exist.
     */
    @DeleteMapping("/{id}")
    public SuccessResponseDto deleteProductById(@PathVariable("id") Integer id) throws NotFound {
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
     * @throws NotFound exception will be thrown if the id is not exist.
     * @throws Existed exception will be thrown if old and new fields values are same.
     */
    @PutMapping("/{id}")
    public SuccessResponseDto updateProductById(@PathVariable("id") Integer id, @RequestBody ProductRequestDto productRequestDto) throws NotFound, Existed {
        return productService.updateProductById(id, productRequestDto);
    }
}
