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
    private StoreRepo storeRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, StockRepo stockRepo, CategoryRepo categoryRepo, StoreRepo storeRepo) {
        this.productRepo = productRepo;
        this.storeRepo = storeRepo;
        this.categoryRepo = categoryRepo;
        this.stockRepo = stockRepo;
    }

    /**
     * <p>
     *     This method used to add product.
     * </p>
     * @param productRequestDto dto type object.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws Existed exception will be thrown if product already exist.
     */
    public SuccessResponseDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound {
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
        return new SuccessResponseDto(201, "Product added successfully");
    }

    /**
     * <p>
     *     This method used to get all products.
     * </p>
     * @return List of products.
     * @throws NotFound exception will be thrown if the products doesn't exist.
     */
    public List<ProductResponseDto> getProducts() throws NotFound {
        List<Product> products = productRepo.findAllAndIsActive(true);
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
     * <P>
     *     This method used to get particular product by id.
     * </P>
     * @param productId to fetch particular product object.
     * @return Dto type product
     * @throws NotFound exception will be thrown if id not exist.
     */
    public ProductResponseDto getProductById(Integer productId) throws NotFound {
        Product product = productRepo.findByIdAndIsActive(productId, true);
        if (!productRepo.existsById(productId)) {
            throw new NotFound("Product id not exist");
        }
        return ProductMapper.toProductDto(product);
    }

    /**
     * <p>
     *     This method to get products in particular category.
     * </p>
     *
     * @param categoryId to get particular object.
     * @return List of products.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsByLocationIdAndCategoryId(Integer locationId, Integer categoryId) throws NotFound {
        List<Product> products = productRepo.findByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        Boolean store = storeRepo.existsByIdAndIsActive(locationId, true);
        if(!store) {
            throw new NotFound("Location id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(locationId, product.getId(), 0);
            if (isStockAvailable) {
                productResponseDto.setIsStockAvailable(true);
            }
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }

    /**
     * <p>
     *     This method used to get products of particular category.
     * </p>
     * @param categoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    public List<ProductResponseDto> getProductsByCategoryId( Integer categoryId) throws NotFound {
        List<Product> products = productRepo.findByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }

    /**
     * <p>
     *     This method used to get products of particular sub category.
     * </p>
     * @param subCategoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    public List<ProductResponseDto> getProductsBySubCategoryId( Integer subCategoryId) throws NotFound {
        List<Product> products = productRepo.findBySubCategoryIdAndIsActive( subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }

    /**
     * <p>
     *     This method will get products in particular sub category.
     * </p>
     *
     * @param subCategoryId to find particular object
     * @return List of products.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsByLocationIdAndSubCategoryId(Integer locationId, Integer subCategoryId) throws NotFound {
        List<Product> products = productRepo.findBySubCategoryIDAndIsActive(subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("Products not found, id invalid");
        }
        Boolean store = storeRepo.existsByIdAndIsActive(locationId, true);
        if(!store) {
            throw new NotFound("Location id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(locationId, product.getId(), 0);
            if (isStockAvailable) {
                productResponseDto.setIsStockAvailable(true);
            }
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }

    /**
     * <p>
     *     This method used to delete product.
     * </p>
     *
     * @param id to find particular object to get delete.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     */
    @Override
    public SuccessResponseDto deleteProductById(Integer id) throws NotFound {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFound("product id not found");
        }
        product.setActive(false);
        productRepo.save(product);
        return new SuccessResponseDto(200, "Product deleted successfully");
    }

    /**
     * <p>
     *     This method used to update product.
     * </p>
     *
     * @param id to find particular object.
     * @param productRequestDto dto type object contains values to get update.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     * @throws Existed exception will be thrown if the values to update is already exist.
     */
    @Override
    public SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto) throws NotFound, Existed {
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
        return new SuccessResponseDto(200, "Product details updated successfully");
    }

    /**
     * <p>
     *     This method used to get products in particular location.
     * </p>
     * @param locationId to fetch particular product object
     * @return products
     * @throws NotFound exception will be thrown if products not exist
     */
    @Override
    public List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound {
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
        return productResponses;
    }
}
