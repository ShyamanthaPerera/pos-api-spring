package com.example.posapispring.dto.impl;

import com.example.posapispring.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements OrderStatus {

    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private List<OrderDetailDTO> orderDetails;
}
