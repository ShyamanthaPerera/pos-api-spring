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
@Table(name = "item")
public class ItemEntity implements SuperEntity {

    @Id
    private String itemCode;
    private String itemName;
    private int qtyOnHand;
    private double unitPrice;
}
