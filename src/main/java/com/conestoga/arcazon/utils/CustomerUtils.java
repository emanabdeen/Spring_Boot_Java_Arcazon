package com.conestoga.arcazon.Utils;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;

import java.util.ArrayList;
import java.util.List;

public class CustomerUtils {

    public static Customer dtoToEntity(CustomerDto dto){
        Customer customer = new Customer();

        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddressLine1(dto.getAddressLine1());
        customer.setCity(dto.getCity());
        customer.setProvince(dto.getProvince());
        customer.setPostalCode(dto.getPostalCode());
        customer.setCreatedAt(dto.getCreatedAt());
        customer.setUpdatedAt(dto.getUpdatedAt());

        return customer;

    }

    public static CustomerDto entityToDto(Customer customer){

        CustomerDto dto = new CustomerDto();

        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddressLine1(customer.getAddressLine1());
        dto.setCity(customer.getCity());
        dto.setProvince(customer.getProvince());
        dto.setPostalCode(customer.getPostalCode());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());

        return dto;
    }

    public static List<CustomerDto> listEntityToListDto(List<Customer> customerList) {

        List<CustomerDto> dtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            dtoList.add(entityToDto(customer));
        }

        return dtoList;
    }

    public static List<Customer> listDtoToEntity(List<CustomerDto> dtoList) {

        List<Customer> customerList = new ArrayList<>();

        for (CustomerDto dto : dtoList) {
            customerList.add(dtoToEntity(dto));
        }

        return customerList;
    }



}
