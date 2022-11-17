/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.ProductService;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This class implement method of crud operations for Product.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 */
@Service
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    private StockRepo stockRepo;
    private final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private StoreRepo storeRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, StockRepo stockRepo, CategoryRepo categoryRepo,
                              StoreRepo storeRepo) {
        this.productRepo = productRepo;
        this.storeRepo = storeRepo;
        this.categoryRepo = categoryRepo;
        this.stockRepo = stockRepo;
    }

    /**
     * <p>
     *     This method to add product to data base, before that will validate name,
     *     id and finally will allow to add in data base.
     * </p>
     * @param productRequestDto dto type object.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws Existed exception will be thrown if product already exist.
     */
    public SuccessResponseDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound {
        logger.debug("Entered into addProduct method in product service");
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new Existed("Product already added");
        }
        if(!categoryRepo.existsById(productRequestDto.getSubCategoryId())) {
            throw new NotFound("Subcategory id not exist");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getSubCategoryId());
        category.ifPresent(product::setCategory);
        productRepo.save(product);
        logger.debug("addProduct method successfully executed");
        return new SuccessResponseDto(201, "Product added successfully");
    }

    /**
     * <p>
     *     This method used to get all products from the data base which are
     *     all active and return in dto type object.
     * </p>
     * @return List of products.
     * @throws NotFound exception will be thrown if the products doesn't exist.
     */
    public List<ProductResponseDto> getProducts() throws NotFound {
        logger.debug("Entered into getProducts method in product service");
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products.isEmpty()) {
            throw new NotFound("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product :products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        logger.debug("getProducts method successfully executed");
        return productResponse;
    }

    /**
     * <P>
     *     This method used to get particular product by id and convert it in to
     *     dto type object using product mapper and then returns it to product controller.
     * </P>
     * @param productId to fetch particular product object.
     * @return Dto type product
     * @throws NotFound exception will be thrown if id not exist.
     */
    public ProductResponseDto getProductById(Integer productId) throws NotFound {
        logger.debug("Entered into getProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(productId, true);
        if (product == null) {
            throw new NotFound("Product not found");
        }
        logger.debug("getProductsById method successfully executed");
        return ProductMapper.toProductDto(product);
    }

    /**
     * <p>
     *     This method used to get products of particular category using id and
     *     convert it to dto type object and it returns to product controller.
     * </p>
     * @param categoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    public List<ProductResponseDto> getProductsByCategoryId( Integer categoryId) throws NotFound {
        logger.debug("Entered into getProductsByCategoryId method in product service");
        List<Product> products = productRepo.findProductsByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("getProductsByCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * <p>
     *     This method used to get products based on user search.
     * </p>
     * @param name to check with products name in data base.
     * @return list of matched products
     * @throws NotFound exception will be thrown if products not found
     */
    public List<ProductResponseDto> getProductsBySearch(String name)
            throws NotFound {
        List<Product> products = productRepo.findProductBySearch(name);
        if (products.isEmpty()) {
            throw new NotFound("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product :products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        return productResponse;
    }

    /**
     * <p>
     *     This method used to get products of particular sub category using id and
     *     convert it to dto type object and it returns to product controller.
     * </p>
     * @param subCategoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFound {
        logger.debug("Entered into getProductsBySubCategoryId method in product service");
        List<Product> products = productRepo.findBySubCategoryIdAndIsActive( subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("getProductsBySubCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * <p>
     *     This method used to delete product using id
     *     and returns success response dto includes message and status code.
     * </p>
     * @param id to find particular object to get delete.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     */
    @Override
    public SuccessResponseDto deleteProductById(Integer id) throws NotFound {
        logger.debug("Entered into deleteProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFound("product id not found");
        }
        product.setActive(false);
        productRepo.save(product);
        logger.debug("deleteProductsById method successfully executed");
        return new SuccessResponseDto(200, "Product deleted successfully");
    }

    /**
     * <p>
     *     This method used to update product fields in data base before updating
     *     it will validate the product fields and then will update in data base.
     * </p>
     * @param id to find particular object.
     * @param productRequestDto dto type object contains values to get update.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     * @throws Existed exception will be thrown if the values to update is already exist.
     */
    @Override
    public SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
            throws NotFound, Existed {
        logger.debug("Entered into updateProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFound("Product id not found");
        }
        if(productRepo.existsByNameAndPriceAndUnit(productRequestDto.getName(),productRequestDto.getPrice(), productRequestDto.getUnit())) {
            throw new Existed("Product already exist");
        }
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepo.save(product);
        logger.debug("updateProductsById method successfully executed");
        return new SuccessResponseDto(200, "Product details updated successfully");
    }

    /**
     * <p>
     *     This method used to get products in particular location
     *     using location id.
     * </p>
     * @param locationId to fetch particular product object
     * @return products
     * @throws NotFound exception will be thrown if products not exist
     */
    @Override
    public List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound {
        logger.debug("Entered into getProductsLocation method in product service");
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products.isEmpty()) {
            throw new NotFound("No products found");
        }
        Boolean location = storeRepo.existsByIdAndIsActive(locationId, true);
        if(!location) {
            throw new NotFound("Location id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Product product :products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(locationId, product.getId(), 0);
            productResponseDto.setIsStockAvailable(isStockAvailable);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsByLocation method successfully executed");
        return productResponses;
    }
}
