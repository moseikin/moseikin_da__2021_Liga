package com.example.liquibasedemo.controller;

import com.example.liquibasedemo.entity.Customer;
import com.example.liquibasedemo.persistence.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/crud/customer")
@RequiredArgsConstructor
@Api(value = "Customer CRUD operations", description = "Customer CRUD operations")
public class CustomerController {
    private final CustomerRepository customerRepository;

    @ApiOperation(value = "Enumerates all Customer entities")
    @GetMapping
    public List<Customer> enumerate() {
        return customerRepository.findAll();
    }

    @ApiOperation(value = "Store given Customer entity")
    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @ApiOperation(value = "Retrieves Customer entity by it ID")
    @GetMapping("/{id}")
    public Customer get(@PathVariable("id")String id) {
        return customerRepository
                .findById(
                        UUID.fromString(id)
                )
                .get();
    }

    //TODO: добавить и проаннотировать операции удаления сущности Customer, и создания новой пустой сущности Customer
}
