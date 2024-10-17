package com.example.posapispring.service;

import com.example.posapispring.dto.ItemStatus;
import com.example.posapispring.dto.impl.ItemDTO;

import java.util.List;

public interface ItemService {

    void saveItem(ItemDTO itemDTO);
    void deleteItem(String itemCode);
    void updateItem(String itemCode,ItemDTO itemDTO);
    List<ItemDTO> getAllItems();
    ItemStatus getItem(String itemCode);
}
