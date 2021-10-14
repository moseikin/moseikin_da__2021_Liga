package com.example.liquibasedemo.config.service;

import com.example.liquibasedemo.entity.Customer;
import com.example.liquibasedemo.persistence.CustomerRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer get(String id) {
        return customerRepository
                .findById(UUID.fromString(id)).get();
    }

    public void delete(String id) {
        Optional <Customer> optional = customerRepository
                .findById(UUID.fromString(id));
        optional.ifPresent(customerRepository::delete);

    }

}
