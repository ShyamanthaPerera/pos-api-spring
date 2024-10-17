package com.example.posapispring.service.impl;

import com.example.posapispring.customStatusCodes.ErrorStatus;
import com.example.posapispring.dao.ItemDAO;
import com.example.posapispring.dto.ItemStatus;
import com.example.posapispring.dto.impl.ItemDTO;
import com.example.posapispring.entity.impl.ItemEntity;
import com.example.posapispring.exceptions.DataPersistException;
import com.example.posapispring.exceptions.ItemNotFoundException;
import com.example.posapispring.service.ItemService;
import com.example.posapispring.util.IDGeneraters;
import com.example.posapispring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDao;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setItemCode(IDGeneraters.generateItemId());
        ItemEntity saveItem = itemDao.save(mapping.toItemEntity(itemDTO));
        if(saveItem == null){
            throw new DataPersistException("Item Not Saved");
        }
    }

    @Override
    public void deleteItem(String itemCode) {
        Optional<ItemEntity> existItem = itemDao.findById(itemCode);
        if(!existItem.isPresent()){
            throw new ItemNotFoundException("Item ID With" + itemCode + "Not Found");
        }else{
            itemDao.deleteById(itemCode);
        }
    }

    @Override
    public void updateItem(String itemCode, ItemDTO itemDTO) {
        Optional<ItemEntity> tmpItem = itemDao.findById(itemCode);
        if(!tmpItem.isPresent()){
            throw new ItemNotFoundException("Item ID With" + itemCode + "Not Found");
        }else {
            tmpItem.get().setItemName(itemDTO.getItemName());
            tmpItem.get().setQtyOnHand(itemDTO.getQtyOnHand());
            tmpItem.get().setUnitPrice(itemDTO.getUnitPrice());
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemEntity> allItems = itemDao.findAll();
        return mapping.asItemDTOList(allItems);
    }

    @Override
    public ItemStatus getItem(String itemCode) {
        if(itemDao.existsById(itemCode)){
            ItemEntity selectedItem = itemDao.getReferenceById(itemCode);
            return mapping.toItemDto(selectedItem);
        }else {
            return new ErrorStatus(2,"Item Code With" + itemCode
                    + "Not Found");
        }
    }
}
