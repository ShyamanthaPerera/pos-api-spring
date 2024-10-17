package com.example.posapispring.controller;

import com.example.posapispring.customStatusCodes.ErrorStatus;
import com.example.posapispring.dto.CustomerStatus;
import com.example.posapispring.dto.impl.CustomerDTO;
import com.example.posapispring.exceptions.CustomerNotFoundException;
import com.example.posapispring.service.CustomerService;
import com.example.posapispring.util.RegEX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            customerService.saveCustomer(customerDTO);
            logger.info("Customer Save Successfully");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataAccessException e){
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Customer save unsuccessful",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable ("customerId") String customerId){
        try {
            if(!RegEX.customerIdMatcher(customerId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteCustomer(customerId);
            logger.info("Customer delete successful");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            logger.warn("Returning 404 Not Found Status",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.error("Customer delete unsuccessful",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable ("customerId") String customerId,
                                               @RequestBody CustomerDTO customerDTO){

        try {
            if(!RegEX.customerIdMatcher(customerId) || customerDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.updateCustomer(customerId,customerDTO);
            logger.info("Customer Update successful");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            logger.warn("Returning Http 400 Bad Request",e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Customer update unsuccessful",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{customerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getSelectedCustomer(@PathVariable ("customerId") String customerId){
        if(!RegEX.customerIdMatcher(customerId)){
            logger.error("Returning Http 400 Bad Request");
            return new ErrorStatus(1,"Customer ID is not valid");
        }
        return customerService.getCustomer(customerId);
    }
}
