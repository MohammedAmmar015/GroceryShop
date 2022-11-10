package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.service.ProductService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    private CategoryRepo categoryRepo;

    private ProductHelper productHelper;

    private StockRepo stockRepo;


    /**
     * <p>
     *     This method used to add product.
     * </p>
     *
     * @param productRequestDto dto type object.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws Existed will be thrown if product already exist.
     */
    public SuccessDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound {
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new Existed("Product Already Added");
        }
        if(!productRepo.existsById(productRequestDto.getSubCategoryId())) {
            throw new NotFound("Id Not Exist");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getSubCategoryId());
        product.setCategoryId(productRequestDto.getCategoryId());
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
     * @throws NotFound will be thrown if the products doesn't exist.
     */
    public List<ProductResponseDto> getProducts() throws NotFound {
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products == null || products.isEmpty()) {
            throw new NotFound("No Products Found");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Product product :products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThanEqual(1, product.getId(), 0);
            if (isStockAvailable) {
                productResponseDto.setIsStockAvailable(true);
            }
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }


    /**
     * <p>
     *     This method to get products in particular category.
     * </p>
     *
     * @param categoryId to get particular object.
     * @return List of products.
     * @throws NotFound will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFound {
        List<Product> products = productRepo.findByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("No Products Found, Id Invalid");
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
     * @throws NotFound will be thrown if the id doesn't exist.
     */
    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFound {
        List<Product> products = productRepo.findBySubCategoryIDAndIsActive(subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFound("No Products Found, Id Invalid");
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
     * @throws NotFound will be thrown if the id doesn't exist.
     */
    @Override
    public SuccessDto deleteProductById(Integer id) throws NotFound {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFound("Id Not Exist");
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
     * @throws NotFound will be thrown if the id doesn't exist.
     * @throws Existed will be thrown if the values to update is already exist.
     */
    @Override
    public SuccessDto updateProductById(Integer id, ProductRequestDto productRequestDto) throws NotFound, Existed {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFound("Product Id Not Found");
        }
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new Existed("Product Already Exist");
        }
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepo.save(product);
        return new SuccessDto(200, "Updated Successfully");
    }

    @Override
    public List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound {
        List<Product> products = productRepo.findAllAndIsActive(true);

        if (products == null || products.isEmpty()) {
            throw new NotFound("No Products Found");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Product product :products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThanEqual(locationId, product.getId(), 0);
            if (isStockAvailable) {
                productResponseDto.setIsStockAvailable(true);
            } else {
                productResponseDto.setIsStockAvailable(false);
            }
            productResponses.add(productResponseDto);
        }
        return productResponses;
    }
}
