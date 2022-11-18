/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.OrderDeliveryMapper;
import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.repository.OrderDeliveryRepository;
import com.ideas2it.groceryshop.service.OrderDeliveryService;
import com.ideas2it.groceryshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 *     OrderDeliveryServiceImpl contains operations related to orderDelivery
 *     which helps the controller to communicate with repository .
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
@RequiredArgsConstructor
public class OrderDeliveryServiceImpl implements OrderDeliveryService {
    private final Logger logger = LogManager.getLogger(OrderService.class);
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final OrderService orderService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto statusUpdate(Integer orderId) throws NotFoundException {
        logger.debug("Entered statusUpdate method in OrderServiceImpl");
        OrderResponseDto OrderResponse = orderService.viewOrderById(orderId);
        if(!OrderResponse.getIsDelivered()) {
            orderDeliveryRepository.updateStatus(orderId);
            logger.debug("Order delivered successfully");
            return new SuccessResponseDto(202, "Order delivered successfully");
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFoundException {
        logger.debug("Entered getDeliveryOrder method in OrderServiceImpl");
        OrderDelivery orderDelivery = orderDeliveryRepository.findByOrderId(orderId);
        if(orderDelivery != null) {
            return OrderDeliveryMapper.entityToDto(orderDelivery);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

}
