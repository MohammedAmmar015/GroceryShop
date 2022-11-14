package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
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

    @Test
    public void placeOrder() throws NotFound {
        SuccessDto successDto = new SuccessDto(202, "Order Placed Successfully");
        UserOrderRequestDto userOrderRequestDto = new UserOrderRequestDto(5,1,1,4);
         when(userOrderService.placeOrder(userOrderRequestDto, 3)).thenReturn(successDto);
         assertEquals(202, successDto.getStatusCode());
    }

    @Test
    public void buyNow() throws NotFound {
        SuccessDto successDto = new SuccessDto(202, "Order Placed Successfully");
        UserOrderRequestDto userOrderRequestDto = new UserOrderRequestDto(5,1,1,4);
        when(userOrderService.buyNow(userOrderRequestDto, 4)).thenReturn(successDto);
        assertEquals(202, successDto.getStatusCode());
    }

    @Test
    public void statusUpdate() throws NotFound{
        SuccessDto successDto = new SuccessDto(202, "Order Delivered Successfully");
        when(userOrderService.statusUpdate(1)).thenReturn(successDto);
        assertEquals(202, successDto.getStatusCode());
    }

    @Test
    public void viewAllActiveOrders() throws NotFound {
        List<UserOrderResponseDto> userOrders = new ArrayList<>();
        List<OrderDetailsResponseDto> orderDetailsResponseDtos =  new ArrayList<>();
        orderDetailsResponseDtos.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        userOrders.add(new UserOrderResponseDto(1, new Date(2022/11/13),200f, orderDetailsResponseDtos,true));
        when(userOrderService.viewAllActiveOrders()).thenReturn(userOrders);
    }

    @Test
    public void viewAllCancelledOrders() throws NotFound {
        List<UserOrderResponseDto> userOrders = new ArrayList<>();
        List<OrderDetailsResponseDto> orderDetailsResponseDtos =  new ArrayList<>();
        orderDetailsResponseDtos.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        userOrders.add(new UserOrderResponseDto(1, new Date(2022/11/13),200f, orderDetailsResponseDtos,false));
        when(userOrderService.viewAllActiveOrders()).thenReturn(userOrders);
    }

    @Test
    public void viewOrderById() throws NotFound {
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto(1, new Date(2022/11/13), 230f, orderDetailsResponse, true);
        Integer orderId = 1;
        when(userOrderService.viewOrderById(orderId)).thenReturn(userOrderResponseDto);
    }
    @Test
    public void viewOrderByUserId() throws NotFound {
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), 230f, orderDetailsResponse, true));
        Integer userId = 1;
        when(userOrderService.viewOrderByUserId(userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }

    @Test
    public void cancelOrder() throws NotFound, Existed {
        Integer orderId = 1;
        SuccessDto successDto = new SuccessDto(202,"Order Cancelled Successfully");
        when(userOrderService.cancelOrderById(orderId)).thenReturn(successDto);
        assertEquals(202,successDto.getStatusCode());
    }

    @Test
    public void viewOrderByProductId() throws NotFound {
        Integer productId = 1;
        List<OrderDetailsResponseDto> orderDetailsResponseDto = new ArrayList<>();
        orderDetailsResponseDto.add(new OrderDetailsResponseDto("Fruits & Vegetables","Fruits", "Apple", 1, 200f));
        when(userOrderService.viewOrdersByProductId(productId)).thenReturn(orderDetailsResponseDto);
    }

    @Test
    public void getDeliveryOrder() throws NotFound {
        Integer userId = 1;
        AddressResponseDto addressResponseDto = new AddressResponseDto(1,"1st Street", "AnnaNagar", 600029, "VR Mall", new Date(2022/11/13), new Date(2022/11/13), 1, 1, true, true);
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto(userId, 1, 200f, addressResponseDto, true);
        when(userOrderService.getDeliveryOrder(userId)).thenReturn(orderDeliveryResponseDto);
        assertEquals(userId, orderDeliveryResponseDto.getUserId());
    }

    @Test
    public void viewOrdersByDate() throws NotFound {
        Date date = new Date(2022/11/13);
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), 230f, orderDetailsResponse, true));
        when(userOrderService.viewOrdersByDate(date)).thenReturn(userOrderResponse);
        assertEquals(date, userOrderResponse.get(0).getOrderedDate());
    }

    @Test
    public void viewOrdersByIdAndDate() throws NotFound, ParseException {
        Integer userId = 1;
        Date date = new Date(2022/11/13);
        List<OrderDetailsResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailsResponseDto("Fruits & Vegetables", "Fruits", "Apple", 2, 200f));
        List<UserOrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new UserOrderResponseDto(1, new Date(2022/11/13), 230f, orderDetailsResponse, true));
        when(userOrderService.viewOrdersByIdAndDate(date, userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }
}
