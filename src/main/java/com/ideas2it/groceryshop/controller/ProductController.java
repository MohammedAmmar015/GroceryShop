package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.ProductService;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     It implements method of CRUD operations for Product.
 * </p>
 * @author Ruban 03/11/2022
 * @version  1.0
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = productService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }








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
    public SuccessDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws Existed, NotFound {
        return productService.addProduct(productRequestDto);

    }

    /**
     * <p>
     *
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

    @GetMapping("/location/{locationId}")
    public List<ProductResponseDto> getProductsByLocation(@PathVariable("locationId") Integer locationId) throws NotFound {
        return productService.getProductsByLocation(locationId);
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
