package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *     This class implement method of crud operations for Product.
 * </p>
 *
 * @author RUBAN  03/11/2022
 * @version  1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final ProductHelper productHelper;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo,
                              ProductHelper productHelper) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.productHelper = productHelper;

    }

    /**
     * <p>
     *     This method used to add product.
     * </p>
     *
     * @param productRequestDto dto type object.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws ExistedException will be thrown if product already exist.
     */
    public SuccessDto addProduct(ProductRequestDto productRequestDto) throws ExistedException {
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new ExistedException("Product Already Added");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getSubCategoryId());
        product.setCategory(category.get());
        productRepo.save(product);
        return new SuccessDto(201, "Added Successfully");
    }

    /**
     * <p>
     *     This method used to get all products.
     * </p>
     *
     * @return List of products.
     * @throws NotFoundException will be thrown if the products doesn't exist.
     */
    public List<ProductResponseDto> getProducts() throws NotFoundException {
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products == null || products.isEmpty()) {
            throw new NotFoundException("Products Not Found");
        }
        List<ProductResponseDto> productResponseDto = new ArrayList<>();
        for (Product product :products) {
            productResponseDto.add(ProductMapper.toProductDto(product));
        }
        return productResponseDto;
    }

    /**
     * <p>
     *     This method to get particular product by id.
     * </p>
     *
     * @param id to find particular object.
     * @return product.
     * @throws NotFoundException will be thrown if the id doesn't exist.
     */
    public ProductResponseDto getProductById(Integer id) throws NotFoundException {
        try {
            Product product = productHelper.getProductById(id);
            return ProductMapper.toProductDto(product);
        } catch(NullPointerException exception) {
            throw new NotFoundException("Id Not Exist");
        }
    }

    /**
     * <p>
     *     This method to get products in particular category.
     * </p>
     *
     * @param categoryId to get particular object.
     * @return List of products.
     * @throws NotFoundException will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFoundException {
        List<Product> products = productRepo.findByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFoundException("No Products Found, Id Invalid");
        }
        return products.stream().map(ProductMapper::toProductDto).collect(Collectors.toList());
    }

    /**
     * <p>
     *     This method will get products in particular sub category.
     * </p>
     *
     * @param subCategoryId to find particular object
     * @return List of products.
     * @throws NotFoundException will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFoundException {
        List<Product> products = productRepo.findBySubCategoryIDAndIsActive(subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFoundException("No Products Found, Id Invalid");
        }
        return products.stream().map(ProductMapper::toProductDto).collect(Collectors.toList());
    }

    /**
     * <p>
     *     This method used to delete product.
     * </p>
     *
     * @param id to find particular object to get delete.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if the id doesn't exist.
     */
    @Override
    public SuccessDto deleteProductById(Integer id) throws NotFoundException {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("Id Not Exist");
        }
        product.setActive(false);
        productRepo.save(product);
        return new SuccessDto(200, "Deleted Successfully");
    }

    /**
     * <p>
     *     This method used to update product.
     * </p>
     *
     * @param id to find particular object.
     * @param productRequestDto dto type object contains values to get update.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if the id doesn't exist.
     * @throws ExistedException will be thrown if the values to update is already exist.
     */
    @Override
    public SuccessDto updateProductById(Integer id, ProductRequestDto productRequestDto) throws NotFoundException, ExistedException {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("Product Id Not Found");
        }
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new ExistedException("Product Already Exist");
        }
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepo.save(product);
        return new SuccessDto(200, "Updated Successfully");
    }
}
