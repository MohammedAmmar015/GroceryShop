/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepository;
import com.ideas2it.groceryshop.repository.ProductRepository;
import com.ideas2it.groceryshop.service.CategoryService;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.StockService;
import com.ideas2it.groceryshop.service.StoreService;
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
 *     This class implement method for create, view, update and delete
 *     operations for product
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 */
@Service
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepository;
    private StockService stockService;
    private StoreService storeService;
    private Logger logger;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, StockService stockService,
                              StoreService storeService, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepo = productRepo;
        this.storeService = storeService;
        this.stockService = stockService;
        this.logger = LogManager.getLogger(ProductServiceImpl.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addProduct(ProductRequestDto productRequestDto)
            throws ExistedException, NotFoundException {
        logger.debug("Entered into addProduct method in product service");
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new ExistedException("Product already added");
        }
        if(categoryRepository.existById(productRequestDto.getSubCategoryId())) {
            throw new NotFoundException("Subcategory id not found");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepository.findById(productRequestDto.getSubCategoryId());
        category.ifPresent(product::setCategory);
        productRepo.save(product);
        logger.debug("addProduct method successfully executed");
        return new SuccessResponseDto(201, "Product added successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProducts() throws NotFoundException {
        logger.debug("Entered into getProducts method in product service");
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        logger.debug("getProducts method successfully executed");
        return productResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductResponseDto getProductResponseById(Integer productId) throws NotFoundException {
        logger.debug("Entered into getProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(productId, true);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        logger.debug("getProductsById method successfully executed");
        return ProductMapper.toProductDto(product);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepo.findByIdAndIsActive(productId, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsByCategoryId( Integer categoryId)
            throws NotFoundException {
        logger.debug("Entered into getProductsByCategoryId method in product service");
        List<Product> products = productRepo.findProductsByCategoryIdAndIsActive( categoryId,
                true);
        if(products.isEmpty()) {
            throw new NotFoundException("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product : products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("getProductsByCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsBySearch(String name)
            throws NotFoundException {
        List<Product> products = productRepo.findProductBySearch(name);
        if (products.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        return productResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId)
            throws NotFoundException {
        logger.debug("Entered into getProductsBySubCategoryId method in product service");
        List<Product> products = productRepo.findBySubCategoryIdAndIsActive(subCategoryId,
                true);
        if(products.isEmpty()) {
            throw new NotFoundException("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product : products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsBySubCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteProductById(Integer id) throws NotFoundException {
        logger.debug("Entered into deleteProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("product not found");
        }
        product.setActive(false);
        productRepo.save(product);
        logger.debug("deleteProductsById method successfully executed");
        return new SuccessResponseDto(200, "Product deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
            throws NotFoundException, ExistedException {
        logger.debug("Entered into updateProductsById method in product service");
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("Product id not found");
        }
        if(productRepo.existsByNameAndPriceAndUnit(productRequestDto.getName(),
                productRequestDto.getPrice(), productRequestDto.getUnit())) {
            throw new ExistedException("Product already exist");
        }
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepo.save(product);
        logger.debug("updateProductsById method successfully executed");
        return new SuccessResponseDto(200, "Product details updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFoundException {
        logger.debug("Entered into getProductsLocation method in product service");
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found");
        }
        Boolean location = storeService.existByLocationId(locationId);
        if(!location) {
            throw new NotFoundException("Location id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockService.
                    getStocksAvailabilityByStoreLocationAndProduct(locationId, product.getId());
            productResponseDto.setIsStockAvailable(isStockAvailable);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsByLocation method successfully executed");
        return productResponses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getProductByCategoryId(Integer categoryId) {
        List<Product> products = productRepo.findProductsByCategoryIdAndIsActive(categoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getProductBySubCategoryId(Integer subCategoryId) {
        List<Product> products = productRepo.findBySubCategoryIdAndIsActive(subCategoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
    }
}
