package com.example.posapispring.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {

    private OrderDTO order;
    private List<OrderDetailDTO> orderDetails;
}
