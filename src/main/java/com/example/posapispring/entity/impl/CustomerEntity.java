package com.example.posapispring.entity.impl;

import com.example.posapispring.entity.SuperEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity implements SuperEntity {

    @Id
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerTel;
}
