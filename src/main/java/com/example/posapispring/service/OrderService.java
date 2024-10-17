package com.example.posapispring.service;

import com.example.posapispring.dto.impl.OrderDTO;
import com.example.posapispring.dto.impl.OrderDetailDTO;

import java.util.List;
public interface OrderService {

    void placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailsDTOS);
    List<OrderDetailDTO> getAllDetails();
}
