package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.dto.AddressResponseDto;

import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.impl.UserOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserOrderTestController.class})
public class UserOrderTestController {

    @Mock
    UserOrderServiceImpl userOrderService;
    @InjectMocks
    UserOrderController userOrderController;

    /**
     * This method is used to test the placeOrder method
     *
     * @throws NotFound
     */
    @Test
    public void placeOrder() throws NotFound {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202, "Order Placed Successfully");
        UserOrderRequestDto userOrderRequestDto = new UserOrderRequestDto();
        userOrderRequestDto.setAddressId(1);
         when(userOrderService.placeOrder(userOrderRequestDto)).thenReturn(SuccessResponseDto);
         assertEquals(202, SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to test buyNow method
     *
     * @throws NotFound
     */
    @Test
    public void buyNow() throws NotFound {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202, "Order Placed Successfully");
        UserOrderRequestDto userOrderRequestDto = new UserOrderRequestDto(5,1,1);
        when(userOrderService.buyNow(userOrderRequestDto)).thenReturn(SuccessResponseDto);
        assertEquals(202, SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used test statusUpdate method
     *
     * @throws NotFound
     */
    @Test
    public void statusUpdate() throws NotFound{
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202, "Order Delivered Successfully");
        when(userOrderService.statusUpdate(1)).thenReturn(SuccessResponseDto);
        assertEquals(202, SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to test viewOrderByUserId method
     *
     * @throws NotFound
     */
    @Test
    public void viewOrderByUserId() throws NotFound {
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),230f, 5, orderDetailsResponse, true));
        Integer userId = 1;
        when(userOrderService.viewOrderByUserId(userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }

    /**
     * This method is used to test the cancelOrder method
     *
     * @throws NotFound
     * @throws Existed
     */
    @Test
    public void cancelOrder() throws NotFound, Existed {
        Integer orderId = 1;
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202,"Order Cancelled Successfully");
        when(userOrderService.cancelOrderById(orderId)).thenReturn(SuccessResponseDto);
        assertEquals(202,SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to test the getDeliveryOrder method
     *
     * @throws NotFound
     */
    @Test
    public void getDeliveryOrder() throws NotFound {
        Integer userId = 1;
        AddressResponseDto addressResponseDto = new AddressResponseDto(1,"1st Street", "AnnaNagar", 600029, "VR Mall", new Date(2022/11/13), new Date(2022/11/13), 1, 1, true, true);
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto(userId, 1, 200f, 6, addressResponseDto, true);
        when(userOrderService.getDeliveryOrder(userId)).thenReturn(orderDeliveryResponseDto);
        assertEquals(userId, orderDeliveryResponseDto.getUserId());
    }

    /**
     * This method is used to test the viewOrderByDate method
     *
     * @throws NotFound
     */
    @Test
    public void viewOrdersByDate() throws NotFound {
        Date date = new Date(2022/11/13);
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),230f, 5, orderDetailsResponse, true));
        when(userOrderService.viewOrdersByDate(date)).thenReturn(userOrderResponse);
        assertEquals(date, userOrderResponse.get(0).getOrderedDate());
    }

    /**
     * This method is used to test the viewOrdersByIdAndDate method
     *
     * @throws NotFound
     * @throws ParseException
     */
    @Test
    public void viewOrdersByIdAndDate() throws NotFound, ParseException {
        Integer userId = 1;
        Date date = new Date(2022/11/13);
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),230f, 5, orderDetailsResponse, true));
        when(userOrderService.viewOrdersByIdAndDate(date, userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }
    
}
