package com.example.posapispring.service.impl;

import com.example.posapispring.dao.ItemDAO;
import com.example.posapispring.dao.OrderDAO;
import com.example.posapispring.dao.OrderDetailDAO;
import com.example.posapispring.dto.impl.OrderDTO;
import com.example.posapispring.dto.impl.OrderDetailDTO;
import com.example.posapispring.entity.impl.ItemEntity;
import com.example.posapispring.entity.impl.OrderDetailEntity;
import com.example.posapispring.entity.impl.OrderEntity;
import com.example.posapispring.exceptions.DataPersistException;
import com.example.posapispring.service.OrderService;
import com.example.posapispring.util.IDGeneraters;
import com.example.posapispring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDao;
    @Autowired
    private OrderDetailDAO orderDetailsDao;
    @Autowired
    private ItemDAO itemDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailsDTOS) {
        try {
            orderDTO.setOrderId(IDGeneraters.generateOrderId());
            OrderEntity saveOrder = orderDao.save(mapping.toOrderEntity(orderDTO));
            if(saveOrder == null){
                throw new DataPersistException("Order not saved");
            }

            for(OrderDetailDTO orderDetailsDTO : orderDetailsDTOS){
                orderDetailsDTO.setDetailsId(IDGeneraters.generateOrderDetailsId());
                OrderDetailEntity orderDetailsEntity = mapping.toOrderDetailsEntity(orderDetailsDTO);
                orderDetailsEntity.setOrder(saveOrder);
                orderDetailsDao.save(orderDetailsEntity);

                Optional<ItemEntity> item = itemDao.findById(String.valueOf(orderDetailsDTO.getItem()));

                if(item.isPresent()){
                    ItemEntity itemEntity = item.get();
                    int updateQty = itemEntity.getQtyOnHand() - orderDetailsDTO.getOrderQty();

                    if(updateQty < 0){
                        throw new DataPersistException("Insufficient stock :" + orderDetailsDTO.getItem());
                    }

                    itemEntity.setQtyOnHand(updateQty);
                    itemDao.save(itemEntity);
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<OrderDetailDTO> getAllDetails() {
        List<OrderDetailEntity> allOrders = orderDetailsDao.findAll();
        return mapping.asOrderDetailsDTOList(allOrders);
    }
}
