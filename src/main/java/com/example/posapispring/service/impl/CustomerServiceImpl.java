package com.example.posapispring.service.impl;

import com.example.posapispring.dao.CustomerDAO;
import com.example.posapispring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDao;

    @Autowired
    private Mapping mapping;
    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerId(AppUtil.generateCustomerId());
        CustomerEntity saveCustomer = customerDao.save(mapping.toCustomerEntity(customerDTO));
        if(saveCustomer == null){
            throw new DataPersistException("Customer not saved");
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        Optional<CustomerEntity> existCustomer = customerDao.findById(customerId);
        if(!existCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer ID With" + customerId + "Not Found");
        }else{
            customerDao.deleteById(customerId);
        }
    }

    @Override
    public void updateCustomer(String customerId, CustomerDTO customerDTO) {
        Optional<CustomerEntity> tmpCustomer = customerDao.findById(customerId);
        if(!tmpCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer ID With" + customerId + "Not Found");
        }else{
            tmpCustomer.get().setCustomerName(customerDTO.getCustomerName());
            tmpCustomer.get().setCustomerAddress(customerDTO.getCustomerAddress());
            tmpCustomer.get().setCustomerTel(customerDTO.getCustomerTel());
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> allCustomers = customerDao.findAll();
        return mapping.asCustomerDTOList(allCustomers);
    }

    @Override
    public CustomerStatus getCustomer(String customerId) {
        if(customerDao.existsById(customerId)){
            CustomerEntity selectedCustomer = customerDao.getReferenceById(customerId);
            return mapping.toCustomerDto(selectedCustomer);
        }else {
            return new SelectedCustomerAndItemAndOrderErrorStatus(2,"Customer ID With" +
                    customerId + "Not Found");
        }
    }
}
