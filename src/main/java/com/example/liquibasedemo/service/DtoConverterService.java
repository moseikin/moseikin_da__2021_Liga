package com.example.liquibasedemo.service;

import com.example.liquibasedemo.dto.CustomerDto;
import com.example.liquibasedemo.entity.Customer;
import com.example.liquibasedemo.persistence.CustomerRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DtoConverterService {

    private final ModelMapper modelMapper = new ModelMapper();

    public CustomerDto toCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }


    public Customer toCustomer(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
