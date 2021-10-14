package com.example.liquibasedemo.controller;

import com.example.liquibasedemo.config.service.BasicService;
import com.example.liquibasedemo.entity.Customer;
import com.example.liquibasedemo.persistence.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crud/customer")
@RequiredArgsConstructor
@Api(value = "Customer CRUD operations", description = "Customer CRUD operations")
public class CustomerController {
//    private final CustomerRepository customerRepository;

    @Autowired
    private BasicService basicService;

    @ApiOperation(value = "Enumerates all Customer entities")
    @GetMapping
    public List<Customer> enumerate() {
        return basicService.findAll();
    }

    @ApiOperation(value = "Store given Customer entity")
    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return basicService.save(customer);
    }

    @ApiOperation(value = "Retrieves Customer entity by it ID")
    @GetMapping("/{id}")
    public Customer get(@PathVariable("id")String id) {
        return basicService.get(id);
    }

    @ApiOperation(value = "Deletes Customer entity")
    @DeleteMapping
    public void delete(@RequestBody String id) {
        basicService.delete(id);
    }

    @ApiOperation(value = "Creates Customer entity")
    @PutMapping
    public Customer create() {
        Customer customer = new Customer();
        return basicService.save(customer);
    }

    //TODO: добавить и проаннотировать операции удаления сущности Customer, и создания новой пустой сущности Customer
}
