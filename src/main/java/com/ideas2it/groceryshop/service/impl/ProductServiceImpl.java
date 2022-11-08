package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
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

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    private CategoryRepo categoryRepo;

    private ProductHelper productHelper;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, ProductHelper productHelper) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.productHelper = productHelper;

    }

    public String addProduct(ProductRequestDto productRequestDto) throws Existed {
        if(productRepo.existsByName(productRequestDto.getName())) {
            throw new Existed("Product Already Added");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getSubCategoryId());
        product.setCategory(category.get());
        product.setCategoryId(productRequestDto.getCategoryId());
        productRepo.save(product);
        return "Saved Successfully";
    }

    public List<ProductResponseDto> getProducts() throws NotFoundException {
        List<Product> products = productRepo.findAllAndIsActive(true);
        if (products == null || products.isEmpty()) {
            throw new NotFoundException("Found No Products");
        }
        List<ProductResponseDto> productResponseDto = new ArrayList<>();
        for (Product product :products) {
            productResponseDto.add(ProductMapper.toProductDto(product));
        }
        return productResponseDto;
    }

    public ProductResponseDto getProductById(Integer id) throws NotFoundException {
        try {
            Product product = productHelper.getProductById(id);
            return ProductMapper.toProductDto(product);
        } catch(NullPointerException exception) {
            throw new NotFoundException("Id Not Exist");
        }
    }

    @Override
    public List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFoundException {
        List<Product> products = productRepo.findByCategoryIdAndIsActive( categoryId, true);
        if(products.isEmpty()) {
            throw new NotFoundException("No Products Found, Id Invalid");
        }
        return products.stream().map(ProductMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFoundException {
        List<Product> products = productRepo.findBySubCategoryIDAndIsActive(subCategoryId, true);
        if(products.isEmpty()) {
            throw new NotFoundException("No Products Found, Id Invalid");
        }
        return products.stream().map(ProductMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public String deleteProductById(Integer id) throws NotFoundException {
        Product product = productRepo.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("Id Not Exist");
        }
        product.setActive(false);
        productRepo.save(product);
        return "Deleted Successfully";
    }

    @Override
    public String updateProductById(Integer id, ProductRequestDto productRequestDto) {
        Product product = productRepo.findByIdAndIsActive(id, true);
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepo.save(product);
        return "Updated Successfully";
    }
}
