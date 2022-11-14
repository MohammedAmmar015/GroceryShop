package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockControllerTest {

    @InjectMocks
    private StockController stockController;

    @Mock
    private StockService stockService;

    @Test
    public void testCreateStock() throws Existed, NotFound {
        Integer locationId = 1;
        Integer productId = 1;
        StockRequestDto stockRequestDto = new StockRequestDto(100);
        SuccessDto successDto = new SuccessDto(201, "Stock Created Successfully");
        when(stockService.addStock(stockRequestDto, locationId, productId)).thenReturn(successDto);
        assertEquals(successDto.getStatusCode(),
                stockController.createStock(stockRequestDto, locationId, productId).getStatusCode());
    }

    @Test
    public void testUpdateStock() throws Existed, NotFound {
        Integer locationId = 1;
        Integer productId = 1;
        StockRequestDto stockRequestDto = new StockRequestDto(100);
        SuccessDto successDto = new SuccessDto(200, "Stock Updated Successfully");
        when(stockService.updateStockByProductAndLocation(stockRequestDto, locationId, productId))
                .thenReturn(successDto);
        assertEquals(successDto.getStatusCode(),
                stockController.updateStockByProductAndLocation(stockRequestDto,
                                                                    locationId,
                                                                    productId).getStatusCode());
    }

    @Test
    public void testViewStockByProduct() throws NotFound {
        Integer productId = 1;
        List<StockResponseDto> stocks = new ArrayList<>();
        stocks.add(new StockResponseDto(1, 100, 1,
                                        "Apple", "Fruits",
                            "Fruits & Vegetable", "Guindy", 60001));
        stocks.add(new StockResponseDto(1, 100, 1,
                "Apple", "Fruits",
                "Fruits & Vegetable", "Anna Nagar", 60002));
        when(stockService.getStockByProductId(productId)).thenReturn(stocks);
        assertEquals(stocks.size(), stockController.viewStockByProduct(productId).size());
    }

    @Test
    public void testViewStockByProductAndLocation() throws NotFound {
        Integer productId = 1;
        Integer locationId = 1;
        StockResponseDto stock = new StockResponseDto(1, 100, 1,
                "Apple", "Fruits",
                "Fruits & Vegetable", "Guindy", 60001);
        when(stockService.getStockByProductAndLocation(productId, locationId)).thenReturn(stock);
        assertEquals(productId,
                stockController.getStockByProductAndLocation(productId, locationId).getProductId());
    }


}
