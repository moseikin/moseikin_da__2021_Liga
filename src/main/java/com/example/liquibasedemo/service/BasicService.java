package com.example.liquibasedemo.service;

import com.example.liquibasedemo.dto.CustomerDto;
import com.example.liquibasedemo.entity.Customer;
import com.example.liquibasedemo.persistence.CustomerRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor

public class BasicService {
    @Autowired
    private CustomerRepository customerRepository;

    private final DtoConverterService dtoConverter = new DtoConverterService();

    public List<CustomerDto> findAll(){
        System.out.println("customerRepository = " + customerRepository);
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(dtoConverter::toCustomerDto).collect(Collectors.toList());
    }

    public CustomerDto save(CustomerDto customerDto) {
        customerRepository.save(dtoConverter.toCustomer(customerDto));
        return customerDto;
    }

    public CustomerDto get(String id) {
        Optional <Customer> optional = customerRepository.findById(UUID.fromString(id));
        if (optional.isPresent()) {
            return dtoConverter.toCustomerDto(optional.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(String id) {
        Optional <Customer> optional = customerRepository.findById(UUID.fromString(id));
        if (optional.isPresent()) {
            customerRepository.delete(optional.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

}
