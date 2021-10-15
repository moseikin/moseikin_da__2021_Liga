package com.example.liquibasedemo.controller;

import com.example.liquibasedemo.dto.CustomerDto;
import com.example.liquibasedemo.service.BasicService;
import com.example.liquibasedemo.entity.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crud/customer")
@RequiredArgsConstructor
@Api(value = "Customer CRUD operations", description = "Customer CRUD operations")
public class CustomerController {
    @Autowired
    private BasicService basicService;

    @ApiOperation(value = "Enumerates all Customer entities")
    @GetMapping
    public List<CustomerDto> enumerate() {
        return basicService.findAll();
    }

    @ApiOperation(value = "Store given Customer entity")
    @PostMapping
    public CustomerDto save(@RequestBody CustomerDto customerDto) {
        return basicService.save(customerDto);
    }

    @ApiOperation(value = "Retrieves Customer entity by it ID")
    @GetMapping("/{id}")
    public CustomerDto get(@PathVariable("id")String id) {
        return basicService.get(id);
    }

    @ApiOperation(value = "Deletes Customer entity")
    @DeleteMapping
    public void delete(@RequestBody String id) {
        basicService.delete(id);
    }

    @ApiOperation(value = "Creates Customer entity")
    @PutMapping
    public CustomerDto create() {
        return basicService.save(new CustomerDto());
    }

    //TODO: добавить и проаннотировать операции удаления сущности Customer, и создания новой пустой сущности Customer
}
