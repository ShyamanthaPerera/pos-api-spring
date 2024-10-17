package com.example.posapispring.util;

import com.example.posapispring.dao.ItemDAO;
import com.example.posapispring.dto.impl.CustomerDTO;
import com.example.posapispring.dto.impl.ItemDTO;
import com.example.posapispring.dto.impl.OrderDTO;
import com.example.posapispring.dto.impl.OrderDetailDTO;
import com.example.posapispring.entity.impl.CustomerEntity;
import com.example.posapispring.entity.impl.ItemEntity;
import com.example.posapispring.entity.impl.OrderDetailEntity;
import com.example.posapispring.entity.impl.OrderEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemDAO itemDao;

    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO,CustomerEntity.class);
    }

    public CustomerDTO toCustomerDto(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity,CustomerDTO.class);
    }

    public List<CustomerDTO> asCustomerDTOList(List<CustomerEntity> customerEntityList){
        return modelMapper.map(customerEntityList, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    public ItemEntity toItemEntity(ItemDTO itemDTO){
        return modelMapper.map(itemDTO, ItemEntity.class);
    }

    public ItemDTO toItemDto(ItemEntity itemEntity){
        return modelMapper.map(itemEntity, ItemDTO.class);
    }

    public List<ItemDTO> asItemDTOList(List<ItemEntity> itemEntityList){
        return modelMapper.map(itemEntityList, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    public OrderEntity toOrderEntity(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, OrderEntity.class);
    }

    public OrderDetailEntity toOrderDetailsEntity(OrderDetailDTO orderDetailsDTO){
        return modelMapper.map(orderDetailsDTO,OrderDetailEntity.class);
    }

    public List<OrderDetailDTO> asOrderDetailsDTOList(List<OrderDetailEntity> orderDetailsEntities){
        return modelMapper.map(orderDetailsEntities,new TypeToken<List<OrderDetailDTO>>() {}.getType());
    }
}
