package com.example.posapispring.customStatusCodes;

import com.example.posapispring.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorStatus implements CustomerStatus, ItemStatus, OrderStatus, OrderDetailStatus, OrderRequestStatus {

    private int statusCode;
    private String statusMessage;
}
